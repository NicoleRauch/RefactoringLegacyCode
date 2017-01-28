package pull.months;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.chrono.ISOChronology;

import common.Transaction_API;

public class ValuesOfMonth implements IValuesOfMonth
{

	protected final YearMonth yearMonth;
	protected final IValuesOfMonth precedingMonth;
	protected final List<Transaction_API> transactions = new ArrayList<Transaction_API>();

	public ValuesOfMonth(LocalDate date, IValuesOfMonth month)
	{
		super();
		this.precedingMonth = month;
		this.yearMonth = new YearMonth(date);
	}

	public YearMonth getYearMonth()
	{
		return yearMonth;
	}

	public void addTransaction(Transaction_API transaction)
	{
		transactions.add(transaction);
	}

	@Override
	public int getBalance()
	{
		int result = precedingMonth.getBalance();
		for (Transaction_API transaction : transactions)
		{
			result += transaction.getAmount();
		}
		return result;
	}

	public int getAverageBalance()
	{
		int result = precedingMonth.getBalance();
		for (Transaction_API transaction : transactions)
		{
			result += calculateProportion(transaction);
		}

		return result;
	}

	private double calculateProportion(Transaction_API transaction)
	{
		int countingDays = daysInMonth() - transaction.getDate().getDayOfMonth() + 1;
		double rate = (double) countingDays / daysInMonth();
		return (transaction.getAmount() * rate);
	}

	private int daysInMonth()
	{
		return ISOChronology.getInstance().dayOfMonth().getMaximumValue(yearMonth);
	}

}
