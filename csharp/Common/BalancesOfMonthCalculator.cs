using System.Collections.Generic;

namespace Common
{
    /// We need this interface to simplify testing.
    public interface BalancesOfMonthCalculator
    {
        void FillData(IList<BalancesOfMonth> balances);
    }
}
