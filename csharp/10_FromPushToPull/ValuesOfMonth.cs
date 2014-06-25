using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace FromPushToPull
{
    public class ValuesOfMonth
    {
        private readonly DateTime dateOfMonth;
        private readonly IList<Transaction> transactionsOfMonth;
        private readonly int precedingBalance;

        public int Balance
        {
            get
            {
                int balance = precedingBalance;
                foreach (Transaction transaction in transactionsOfMonth)
                {
                    balance += transaction.Amount;
                }
                return balance;
            }
        }

        public int AverageBalance
        {
            get
            {
                double averageBalance = precedingBalance;
                foreach (Transaction transaction in transactionsOfMonth)
                {
                    averageBalance += RateOf(transaction);
                }
                return (int)averageBalance;
            }
        }

        public ValuesOfMonth() : this(DateTime.Now, new List<Transaction>(), 0) { }

        public ValuesOfMonth(DateTime dateOfMonth, IList<Transaction> transactionsOfMonth, int precedingBalance)
        {
            this.dateOfMonth = dateOfMonth;
            this.transactionsOfMonth = transactionsOfMonth;
            this.precedingBalance = precedingBalance;
        }

        private double RateOf(Transaction transaction)
        {
            int countingDays = (dateOfMonth - transaction.Date).Days + 1;
            double rate = (double)countingDays / dateOfMonth.Day;
            return transaction.Amount * rate;
        }
    }
}
