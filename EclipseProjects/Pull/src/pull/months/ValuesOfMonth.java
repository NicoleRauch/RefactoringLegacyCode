package pull.months;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.chrono.ISOChronology;

import common.Transaction;

public class ValuesOfMonth implements IValuesOfMonth
{

	protected final YearMonth yearMonth;
	protected final IValuesOfMonth precedingMonth;
	protected final List<Transaction> transactions = new ArrayList<Transaction>();

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

	public void addTransaction(Transaction transaction)
	{
		transactions.add(transaction);
	}

	@Override
	public int getBalance()
	{
		int result = precedingMonth.getBalance();
		for (Transaction transaction : transactions)
		{
			result += transaction.getAmount();
		}
		return result;
	}

	public int getAverageBalance()
	{
		int result = precedingMonth.getBalance();
		for (Transaction transaction : transactions)
		{
			result += calculateProportion(transaction);
		}

		return result;
	}

	private double calculateProportion(Transaction transaction)
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
