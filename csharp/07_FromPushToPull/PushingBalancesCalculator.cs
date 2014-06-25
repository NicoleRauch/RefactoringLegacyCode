using System;
using System.Collections.Generic;
using Common;

namespace FromPushToPull
{
    public class PushingBalancesCalculator : BalancesOfMonthCalculator
    {
        private readonly IList<Transaction> transactions;

        public PushingBalancesCalculator(IList<Transaction> transactions)
        {
            this.transactions = transactions;
        }

        public void FillData(IList<BalancesOfMonth> balancesOfMonthList)
        {
            ValuesOfMonth valuesOfMonth = new ValuesOfMonth();

            foreach (BalancesOfMonth balancesOfMonth in balancesOfMonthList)
            {
                DateTime dateOfMonth = balancesOfMonth.Date;
                IList<Transaction> transactionsOfMonth = TransactionsOfMonth(dateOfMonth);

                int precedingBalance = valuesOfMonth.Balance;

                valuesOfMonth = new ValuesOfMonth(dateOfMonth, transactionsOfMonth, precedingBalance);
                valuesOfMonth.CalculateValues();

                balancesOfMonth.Balance = valuesOfMonth.Balance;
                balancesOfMonth.AverageBalance = valuesOfMonth.AverageBalance;
            }
        }

        private IList<Transaction> TransactionsOfMonth(DateTime date)
        {
            IList<Transaction> results = new List<Transaction>();
            foreach (Transaction transaction in transactions)
            {
                DateTime dateOfTransaction = transaction.Date;
                if (AreSameMonthAndYear(date, dateOfTransaction))
                {
                    results.Add(transaction);
                }
            }
            return results;
        }

        private bool AreSameMonthAndYear(DateTime date, DateTime dateOfTransaction)
        {
            return dateOfTransaction.Month == date.Month && dateOfTransaction.Year == date.Year;
        }

    }
}
