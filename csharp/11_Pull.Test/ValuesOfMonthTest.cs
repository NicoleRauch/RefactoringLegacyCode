using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Common;

namespace Pull.Months.Test
{
    [TestClass]
    public class ValuesOfMonthTest
    {
        private static readonly DateTime APRIL_ULTIMO = new DateTime(2010, 4, 30);
        private static readonly DateTime MAY_ULTIMO = new DateTime(2010, 5, 31);

        [TestMethod]
        public void MonthWithoutTransactionsHasZeroBalance()
        {
            ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, new DummyValuesOfMonth());
            
            Assert.AreEqual(0, valuesOfMonth.Balance);
            Assert.AreEqual(0, valuesOfMonth.AverageBalance);
        }

        [TestMethod]
        public void MonthWithOneTransactionHasBalanceOfThat()
        {
            ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, new DummyValuesOfMonth());
            valuesOfMonth.AddTransaction(new Transaction { Date = new DateTime(2010, 4, 1), Amount = 100 });
            
            Assert.AreEqual(100, valuesOfMonth.Balance);
            Assert.AreEqual(100, valuesOfMonth.AverageBalance);
        }

        [TestMethod]
        public void MonthWithTwoTransactionsHasSumOfThoseAsBalance()
        {
            ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, new DummyValuesOfMonth());
            valuesOfMonth.AddTransaction(new Transaction { Date = new DateTime(2010, 4, 1), Amount = 100 });
            valuesOfMonth.AddTransaction(new Transaction { Date = new DateTime(2010, 4, 16), Amount = 100 });
            
            Assert.AreEqual(200, valuesOfMonth.Balance);
            Assert.AreEqual(150, valuesOfMonth.AverageBalance);
        }

        [TestMethod]
        public void TwoMonthsWithTwoTransactionsHasSumOfThoseAsBalance()
        {
            ValuesOfMonth april = new ValuesOfMonth(APRIL_ULTIMO, new DummyValuesOfMonth());
            april.AddTransaction(new Transaction { Date = new DateTime(2010, 4, 16), Amount = 100 });
            ValuesOfMonth may = new ValuesOfMonth(MAY_ULTIMO, april);
            may.AddTransaction(new Transaction { Date = new DateTime(2010, 5, 16), Amount = 200 });

            Assert.AreEqual(300, may.Balance);
            Assert.AreEqual(203, may.AverageBalance);
        }
    }
}
