package pull;

import java.util.List;

import pull.months.ValuesOfMonth;
import pull.months.Months;

import common.BalancesOfMonth;
import common.BalancesOfMonthCalculator;
import common.Transaction;

public class PullingBalancesCalculator implements BalancesOfMonthCalculator
{

	private final List<Transaction> transactions;

	public PullingBalancesCalculator(List<Transaction> transactions)
	{
		super();
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth> balancesOfMonthList)
	{
		Months months = new Months(balancesOfMonthList, transactions);

		for (BalancesOfMonth balancesOfMonth : balancesOfMonthList)
		{
			ValuesOfMonth accordingMonth = months.forDate(balancesOfMonth.getDate());
			balancesOfMonth.setBalance(accordingMonth.getBalance());
			balancesOfMonth.setAverageBalance(accordingMonth.getAverageBalance());
		}
	}
}
