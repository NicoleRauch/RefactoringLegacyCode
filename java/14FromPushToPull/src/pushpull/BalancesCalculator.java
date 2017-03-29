package pushpull;

import java.util.List;

import common.BalancesOfMonthCalculator_API;
import common.BalancesOfMonth_API;
import common.Transaction_API;

public class BalancesCalculator implements BalancesOfMonthCalculator_API {

	private final Transactions transactions;

	public BalancesCalculator(List<Transaction_API> transactions) {
		this.transactions = new Transactions(transactions);
	}

	@Override
	public void fillData(List<BalancesOfMonth_API> balancesOfMonthList) {
		Months months = new Months(balancesOfMonthList, transactions);

		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			ValuesOfMonth currentMonth = months.monthFor(balancesOfMonth.getDate());
			balancesOfMonth.setBalance(currentMonth.getBalance());
			balancesOfMonth.setAverageBalance(currentMonth.getAverageBalance());
		}
	}
}
