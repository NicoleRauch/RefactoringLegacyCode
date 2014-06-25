using System.Collections.Generic;
using Common;
using Pull.Months;

namespace Pull
{
    public class PullingBalancesCalculator : BalancesOfMonthCalculator
    {
        private readonly IList<Transaction> transactions;

        public PullingBalancesCalculator(IList<Transaction> transactions)
        {
            this.transactions = transactions;
        }

        public void FillData(IList<BalancesOfMonth> balancesOfMonthList)
        {
            Months.Months months = new Months.Months(balancesOfMonthList, transactions);

            foreach (BalancesOfMonth balancesOfMonth in balancesOfMonthList)
            {
                ValuesOfMonth valuesOfMonth = months.ForDate(balancesOfMonth.Date);
                balancesOfMonth.Balance = valuesOfMonth.Balance;
                balancesOfMonth.AverageBalance = valuesOfMonth.AverageBalance;
            }
        }
    }
}
