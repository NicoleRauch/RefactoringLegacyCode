package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.BalancesOfMonthCalculator_API;
import common.BalancesOfMonth_API;
import common.Transaction_API;

public class PullingBalancesCalculator implements BalancesOfMonthCalculator_API {

	private final List<Transaction_API> transactions;

	public PullingBalancesCalculator(List<Transaction_API> transactions) {
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth_API> balancesOfMonthList) {
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();

		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction_API> transactionsOfMonth = transactionsOfMonth(dateOfMonth);

			valuesOfMonth = new ValuesOfMonth(valuesOfMonth, dateOfMonth, transactionsOfMonth);

			balancesOfMonth.setBalance(valuesOfMonth.getBalance());
			balancesOfMonth.setAverageBalance(valuesOfMonth.getAverageBalance());
		}
	}

	private List<Transaction_API> transactionsOfMonth(LocalDate date) {
		List<Transaction_API> results = new ArrayList<Transaction_API>();
		for (Transaction_API transaction : transactions) {
			LocalDate dateOfTransaction = transaction.getDate();
			if (areSameMonthAndYear(date, dateOfTransaction)) {
				results.add(transaction);
			}
		}
		return results;
	}

	private boolean areSameMonthAndYear(LocalDate date, LocalDate dateOfTransaction) {
		return dateOfTransaction.getMonthOfYear() == date.getMonthOfYear() && dateOfTransaction.getYear() == date.getYear();
	}

}
