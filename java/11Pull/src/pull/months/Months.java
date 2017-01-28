package pull.months;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import common.BalancesOfMonth_API;
import common.Transaction_API;

public class Months
{

	private final List<ValuesOfMonth> months = new ArrayList<ValuesOfMonth>();
	private final Map<YearMonth, ValuesOfMonth> monthsInMap = new HashMap<YearMonth, ValuesOfMonth>();

	public Months(List<BalancesOfMonth_API> balancesOfOneAccount, List<Transaction_API> transactions)
	{
		super();
		init(balancesOfOneAccount, transactions);
	}

	private void init(List<BalancesOfMonth_API> balancesOfOneAccount, List<Transaction_API> transactions)
	{
		ValuesOfMonth month = null;
		for (BalancesOfMonth_API balancesOfMonth : balancesOfOneAccount)
		{
			month = new ValuesOfMonthWithCaching(balancesOfMonth.getDate(), month != null ? month : new InitialValuesOfMonth());
			getMonths().add(month);
			monthsInMap.put(month.getYearMonth(), month);
		}

		allocateTransactionsToMonths(transactions);
	}

	public List<ValuesOfMonth> getMonths()
	{
		return months;
	}

	public ValuesOfMonth forDate(LocalDate date)
	{
		return monthsInMap.get(new YearMonth(date));
	}

	private void allocateTransactionsToMonths(List<Transaction_API> transactions)
	{
		for (Transaction_API transaction : transactions)
		{
			forDate(transaction.getDate()).addTransaction(transaction);
		}
	}

}
