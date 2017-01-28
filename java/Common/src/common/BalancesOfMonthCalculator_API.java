package common;

import java.util.List;

/**
 * we need this interface to simplify testing. @see{BerechnerTest}
 */
public interface BalancesOfMonthCalculator_API {

	public abstract void fillData(List<BalancesOfMonth_API> balances);

}
