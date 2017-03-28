package push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import common.BalancesOfMonthCalculator_API;
import common.BalancesOfMonth_API;
import common.Transaction_API;

public class PullingBalancesCalculator implements BalancesOfMonthCalculator_API {

	private final Transactions transactions;

	private final Map<LocalDate, ValuesOfMonth> months = new HashMap<LocalDate, ValuesOfMonth>();

	public PullingBalancesCalculator(List<Transaction_API> transactions) {
		this.transactions = new Transactions(transactions);
	}

	@Override
	public void fillData(List<BalancesOfMonth_API> balancesOfMonthList) {
		ValuesOfMonth valuesOfMonth = null;
		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction_API> transactionsOfMonth = transactions.transactionsOfMonth(dateOfMonth);

			valuesOfMonth = new ValuesOfMonth(valuesOfMonth == null ? new InitialValuesOfMonth() : valuesOfMonth, dateOfMonth,
					transactionsOfMonth);
			months.put(dateOfMonth, valuesOfMonth);
		}

		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			LocalDate dateOfMonth = balancesOfMonth.getDate();

			balancesOfMonth.setBalance(months.get(dateOfMonth).getBalance());
			balancesOfMonth.setAverageBalance(months.get(dateOfMonth).getAverageBalance());
		}
	}
}
