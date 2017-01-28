package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.BalancesOfMonthCalculator_API;
import common.BalancesOfMonth_API;
import common.Transaction_API;

public class PushingBalancesCalculator implements BalancesOfMonthCalculator_API {

	private final List<Transaction_API> transactions;

	public PushingBalancesCalculator(List<Transaction_API> transactions) {
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth_API> balancesOfMonthList) {
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();
		int balance = 0;

		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction_API> transactionsOfMonth = transactionsOfMonth(dateOfMonth);

			valuesOfMonth = calculateValuesForMonth(balance, dateOfMonth, transactionsOfMonth);
			balance = valuesOfMonth.getBalance();

			balancesOfMonth.setBalance(valuesOfMonth.getBalance());
			balancesOfMonth.setAverageBalance(valuesOfMonth.getAverageBalance());
		}
	}

	private ValuesOfMonth calculateValuesForMonth(int precedingBalance, LocalDate dateOfMonth,
			List<Transaction_API> transactionsOfMonth) {
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();

		valuesOfMonth.calculateValues(dateOfMonth, transactionsOfMonth, precedingBalance);
		return valuesOfMonth;
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
