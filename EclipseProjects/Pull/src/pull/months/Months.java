package pull.months;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import common.BalancesOfMonth;
import common.Transaction;

public class Months
{

	private final List<Month> months = new ArrayList<Month>();
	private final Map<YearMonth, Month> monthsInMap = new HashMap<YearMonth, Month>();

	public Months(List<BalancesOfMonth> balancesOfOneAccount, List<Transaction> transactions)
	{
		super();
		init(balancesOfOneAccount, transactions);
	}

	private void init(List<BalancesOfMonth> balancesOfOneAccount, List<Transaction> transactions)
	{
		Month month = null;
		for (BalancesOfMonth balancesOfMonth : balancesOfOneAccount)
		{
			month = new MonthWithCaching(balancesOfMonth.getDate(), month != null ? month : new DummyMonth());
			getMonths().add(month);
			monthsInMap.put(month.getYearMonth(), month);
		}

		allocateTransactionsToMonths(transactions);
	}

	public List<Month> getMonths()
	{
		return months;
	}

	public Month forDate(LocalDate date)
	{
		return monthsInMap.get(new YearMonth(date));
	}

	private void allocateTransactionsToMonths(List<Transaction> transactions)
	{
		for (Transaction transaction : transactions)
		{
			forDate(transaction.getDate()).addTransaction(transaction);
		}
	}

}
