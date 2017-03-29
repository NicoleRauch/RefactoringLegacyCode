package pushpull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import common.BalancesOfMonth_API;
import common.Transaction_API;

public class Months {

	private final Map<LocalDate, ValuesOfMonth> months = new HashMap<LocalDate, ValuesOfMonth>();

	public Months(List<BalancesOfMonth_API> balancesOfMonthList, Transactions transactions) {
		ValuesOfMonth valuesOfMonth = null;
		for (BalancesOfMonth_API balancesOfMonth : balancesOfMonthList) {
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction_API> transactionsOfMonth = transactions.transactionsOfMonth(dateOfMonth);

			valuesOfMonth = new ValuesOfMonthWithCaching(valuesOfMonth == null ? new InitialValuesOfMonth() : valuesOfMonth,
					dateOfMonth, transactionsOfMonth);
			months.put(dateOfMonth, valuesOfMonth);
		}
	}

	public ValuesOfMonth monthFor(LocalDate dateOfMonth) {
		return months.get(dateOfMonth);
	}

}
