using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Common;

namespace FromPushToPull.Test
{
    [TestClass]
    public class PushTest : CalculatorTest
    {
        [TestInitialize]
        public void Setup()
        {
            calculator = new PushingBalancesCalculator(transactions);
        }

        [TestMethod]
        public new void OneTransactionAtFirstOfMonthSetsBalanceAndAverage()
        {
            base.OneTransactionAtFirstOfMonthSetsBalanceAndAverage();
        }

        [TestMethod]
        public new void OneTransactionAtUltimoOfMonthSetsBalanceAndAverage()
        {
            base.OneTransactionAtUltimoOfMonthSetsBalanceAndAverage();
        }

        [TestMethod]
        public new void TwoTransactionAtFirstAndSixteenthOfMonthLeadToHalfAverageOfBalance()
        {
            base.TwoTransactionAtFirstAndSixteenthOfMonthLeadToHalfAverageOfBalance();
        }

        [TestMethod]
        public new void BalanceOfPreviousMonthIsFactoredIn()
        {
            base.BalanceOfPreviousMonthIsFactoredIn();
        }

        [TestMethod]
        public new void BalanceOfPreviousMonthIsFactoredInOverTwoMonths()
        {
            base.BalanceOfPreviousMonthIsFactoredInOverTwoMonths();
        }
    }
}
