using System;
using System.Collections.Generic;
using Common;

namespace Pull.Months
{
    public class ValuesOfMonth : IValuesOfMonth
    {
        private readonly Month month;
        private readonly IValuesOfMonth precedingMonth;
        private readonly IList<Transaction> transactions = new List<Transaction>();

        public virtual int Balance
        {
            get
            {
                int balance = precedingMonth.Balance;
                foreach (Transaction transaction in transactions)
                {
                    balance += transaction.Amount;
                }
                return balance;
            }
        }

        public virtual int AverageBalance
        {
            get
            {
                double averageBalance = precedingMonth.Balance;
                foreach (Transaction transaction in transactions)
                {
                    averageBalance += RateOf(transaction);
                }
                return (int)averageBalance;
            }
        }

        public Month Month
        {
            get { return month; }
        }

        public ValuesOfMonth(DateTime dateOfMonth, IValuesOfMonth precedingMonth)
        {
            this.month = new Month(dateOfMonth);
            this.precedingMonth = precedingMonth;
        }

        public void AddTransaction(Transaction transaction)
        {
            this.transactions.Add(transaction);
        }

        private double RateOf(Transaction transaction)
        {
            int countingDays = month.DaysInMonth - transaction.Date.Day + 1;
            double rate = (double)countingDays / month.DaysInMonth;
            return transaction.Amount * rate;
        }
    }
}
