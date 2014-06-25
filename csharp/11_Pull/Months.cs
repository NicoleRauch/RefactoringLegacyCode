using System;
using System.Collections.Generic;
using Common;
using Pull;

namespace Pull.Months
{
    public class Months
    {
        private readonly IList<ValuesOfMonth> months = new List<ValuesOfMonth>();
        private readonly IDictionary<Month, ValuesOfMonth> monthsInMap = new Dictionary<Month, ValuesOfMonth>();

        public Months(IList<BalancesOfMonth> balancesOfOneAccount, IList<Transaction> transactions)
        {
            Init(balancesOfOneAccount, transactions);
        }

        public ValuesOfMonth ForDate(DateTime date)
        {
            return monthsInMap[new Month(date)];
        }

        private void Init(IList<BalancesOfMonth> balancesOfOneAccount, IList<Transaction> transactions)
        {
            ValuesOfMonth month = null;
            foreach (BalancesOfMonth balancesOfMonth in balancesOfOneAccount)
            {
                month = new ValuesOfMonthWithCaching(balancesOfMonth.Date, month != null ? (IValuesOfMonth)month : new DummyValuesOfMonth());
                months.Add(month);
                monthsInMap[month.Month] = month;
            }

            AllocateTransactionsToMonths(transactions);
        }

        private void AllocateTransactionsToMonths(IList<Transaction> transactions)
        {
            foreach (Transaction transaction in transactions)
            {
                ForDate(transaction.Date).AddTransaction(transaction);
            }
        }
    }
}
