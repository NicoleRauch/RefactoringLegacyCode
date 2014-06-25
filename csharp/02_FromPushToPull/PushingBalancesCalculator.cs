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
            int balance = 0;

            foreach (BalancesOfMonth balancesOfMonth in balancesOfMonthList)
            {
                DateTime dateOfMonth = balancesOfMonth.Date;
                IList<Transaction> transactionsOfMonth = TransactionsOfMonth(dateOfMonth);

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

                valuesOfMonth.Balance = balance;
                valuesOfMonth.AverageBalance = (int)averageBalance;

                balancesOfMonth.Balance = valuesOfMonth.Balance;
                balancesOfMonth.AverageBalance = valuesOfMonth.AverageBalance;
            }
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
