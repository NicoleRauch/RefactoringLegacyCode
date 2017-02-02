package pull.months;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import common.BalancesOfMonth_API;
import common.Transaction_API;

public class Months {

	private final Map<YearMonth, ValuesOfMonth> months = new HashMap<YearMonth, ValuesOfMonth>();

	public Months(List<BalancesOfMonth_API> balancesOfOneAccount, List<Transaction_API> transactions) {
		ValuesOfMonth month = null;
		for (BalancesOfMonth_API balancesOfMonth : balancesOfOneAccount) {
			IValuesOfMonth precedingMonth = month != null ? month : new InitialValuesOfMonth();
			List<Transaction_API> filteredTransactions = transactionsOfMonth(balancesOfMonth.getDate(), transactions);
			month = new ValuesOfMonthWithCaching(balancesOfMonth.getDate(), precedingMonth, filteredTransactions);
			months.put(month.getYearMonth(), month);
		}
	}

	private List<Transaction_API> transactionsOfMonth(LocalDate date, List<Transaction_API> transactions) {
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

	public ValuesOfMonth forDate(LocalDate date) {
		return months.get(new YearMonth(date));
	}
}
