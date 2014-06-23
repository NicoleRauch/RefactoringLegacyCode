using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Common
{
    /// <summary>
    /// <p>This abstract class contains the integration tests for the demo code.</p>
    /// <p>This is done to avoid code duplication as well as to insure that all the tests are testing the same.</p>
    /// </summary>
    [TestClass]
    public abstract class CalculatorTest
    {
        private static readonly DateTime APRIL_ULTIMO = new DateTime(2010, 4, 30);
        private static readonly DateTime MAY_ULTIMO = new DateTime(2010, 5, 31);
        private static readonly DateTime JUNE_ULTIMO = new DateTime(2010, 6, 30);
        private readonly IList<BalancesOfMonth> balances = new List<BalancesOfMonth>();

        protected readonly IList<Transaction> transactions = new List<Transaction>();
        protected BalancesOfMonthCalculator calculator;

        protected void OneTransactionAtFirstOfMonthSetsBalanceAndAverage()
        {
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 1), Amount = 100 });
            balances.Add(new BalancesOfMonth { Date = APRIL_ULTIMO });

            calculator.FillData(balances);

            BalancesOfMonth aprilData = balances[0];
            Assert.AreEqual(100, aprilData.Balance);
            Assert.AreEqual(100, aprilData.AverageBalance);
        }

        protected void OneTransactionAtUltimoOfMonthSetsBalanceAndAverage()
        {
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 30), Amount = 300 });
            balances.Add(new BalancesOfMonth { Date = APRIL_ULTIMO });

            calculator.FillData(balances);

            BalancesOfMonth aprilData = balances[0];
            Assert.AreEqual(300, aprilData.Balance);
            Assert.AreEqual(10, aprilData.AverageBalance);
        }

        protected void TwoTransactionAtFirstAndSixteenthOfMonthLeadToHalfAverageOfBalance()
        {
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 1), Amount = 100 });
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 16), Amount = 200 });
            balances.Add(new BalancesOfMonth { Date = APRIL_ULTIMO });

            calculator.FillData(balances);

            BalancesOfMonth aprilData = balances[0];
            Assert.AreEqual(300, aprilData.Balance);
            Assert.AreEqual(200, aprilData.AverageBalance);
        }

        protected void BalanceOfPreviousMonthIsFactoredIn()
        {
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 16), Amount = 100 });
            transactions.Add(new Transaction { Date = new DateTime(2010, 5, 16), Amount = 200 });
            balances.Add(new BalancesOfMonth { Date = APRIL_ULTIMO });
            balances.Add(new BalancesOfMonth { Date = MAY_ULTIMO });

            calculator.FillData(balances);

            BalancesOfMonth aprilData = balances[0];
            BalancesOfMonth mayData = balances[1];
            Assert.AreEqual(100, aprilData.Balance);
            Assert.AreEqual(50, aprilData.AverageBalance);
            Assert.AreEqual(300, mayData.Balance);
            Assert.AreEqual(203, mayData.AverageBalance);
        }

        protected void BalanceOfPreviousMonthIsFactoredInOverTwoMonths()
        {
            transactions.Add(new Transaction { Date = new DateTime(2010, 4, 16), Amount = 100 });
            transactions.Add(new Transaction { Date = new DateTime(2010, 5, 16), Amount = 200 });
            balances.Add(new BalancesOfMonth { Date = APRIL_ULTIMO });
            balances.Add(new BalancesOfMonth { Date = MAY_ULTIMO });
            balances.Add(new BalancesOfMonth { Date = JUNE_ULTIMO });

            calculator.FillData(balances);

            BalancesOfMonth juneData = balances[2];
            Assert.AreEqual(300, juneData.Balance);
            Assert.AreEqual(300, juneData.AverageBalance);
        }
    }
}
