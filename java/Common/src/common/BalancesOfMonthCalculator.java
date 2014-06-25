package common;

import java.util.List;

/**
 * we need this interface to simplify testing. @see{BerechnerTest}
 */
public interface BalancesOfMonthCalculator
{

	public abstract void fillData(List<BalancesOfMonth> balances);

}
