using System;

namespace Pull.Months
{
    class ValuesOfMonthWithCaching : ValuesOfMonth
    {
        private int? balanceLazy = null;
        private int? averageBalanceLazy = null;

        public ValuesOfMonthWithCaching(DateTime dateOfMonth, IValuesOfMonth precedingMonth)
            : base(dateOfMonth, precedingMonth) { }

        public override int Balance
        {
            get
            {
                if (balanceLazy == null)
                {
                    balanceLazy = base.Balance;
                }
                return balanceLazy.Value;
            }
        }

        public override int AverageBalance
        {
            get
            {
                if (averageBalanceLazy == null)
                {
                    averageBalanceLazy = base.AverageBalance;
                }
                return averageBalanceLazy.Value;
            }
        }
    }
}
