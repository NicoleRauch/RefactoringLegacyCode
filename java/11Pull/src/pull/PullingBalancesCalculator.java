package pull;

import java.util.List;

import pull.months.ValuesOfMonth;
import pull.months.Months;

import common.BalancesOfMonth_API;
import common.BalancesOfMonthCalculator_API;
import common.Transaction_API;

public class PullingBalancesCalculator implements BalancesOfMonthCalculator_API
{

	private final List<Transaction_API> transactions;

	public PullingBalancesCalculator(List<Transaction_API> transactions)
	{
		super();
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth_API> balancesOfMonthList)
	{
		Months months = new Months(balancesOfMonthList, transactions);

		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList)
		{
			ValuesOfMonth accordingMonth = months.forDate(balancesOfMonth.getDate());
			balancesOfMonth.setBalance(accordingMonth.getBalance());
			balancesOfMonth.setAverageBalance(accordingMonth.getAverageBalance());
		}
	}
}
