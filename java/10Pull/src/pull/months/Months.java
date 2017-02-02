package pull.months;

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
		createMonths(balancesOfOneAccount);
		allocateTransactionsToMonths(transactions);
	}

	private void createMonths(List<BalancesOfMonth_API> balancesOfOneAccount) {
		ValuesOfMonth month = null;
		for (BalancesOfMonth_API balancesOfMonth : balancesOfOneAccount) {
			IValuesOfMonth precedingMonth = month != null ? month : new InitialValuesOfMonth();
			month = new ValuesOfMonthWithCaching(balancesOfMonth.getDate(), precedingMonth);
			months.put(month.getYearMonth(), month);
		}
	}

	private void allocateTransactionsToMonths(List<Transaction_API> transactions) {
		for (Transaction_API transaction : transactions) {
			forDate(transaction.getDate()).addTransaction(transaction);
		}
	}

	public ValuesOfMonth forDate(LocalDate date) {
		return months.get(new YearMonth(date));
	}
}
