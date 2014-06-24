using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace FromPushToPull
{
    class ValuesOfMonth
    {
        public int Balance { get; set; }

        public int AverageBalance { get; set; }

        public void CalculateValues(DateTime dateOfMonth, IList<Transaction> transactionsOfMonth, int precedingBalance)
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

            Balance = balance;
            AverageBalance = (int)averageBalance;
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
