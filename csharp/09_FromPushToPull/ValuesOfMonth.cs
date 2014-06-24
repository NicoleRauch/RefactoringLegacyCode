using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace FromPushToPull
{
    class ValuesOfMonth
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
                int balance = precedingBalance;
                int ultimo = dateOfMonth.Day;

                double averageBalance = 0;
                int dayOfLatestBalance = 1;
                foreach (Transaction transaction in transactionsOfMonth)
                {
                    int day = transaction.Date.Day;
                    averageBalance += CalculateProportionalBalance(dayOfLatestBalance, balance, day, ultimo);
                    balance += transaction.Amount;
                    dayOfLatestBalance = day;
                }

                averageBalance += CalculateProportionalBalance(dayOfLatestBalance, balance, ultimo + 1, ultimo);

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

        private double CalculateProportionalBalance(int dayOfLatestBalance, int balance, int day, int daysInMonth)
        {
            int countingDays = day - dayOfLatestBalance;
            if (countingDays == 0)
            {
                return 0;
            }
            double rate = (double)countingDays / daysInMonth;
            return (balance * rate);
        }
    }
}
