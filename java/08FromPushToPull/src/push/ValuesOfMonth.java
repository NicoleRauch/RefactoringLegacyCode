package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction;

public class ValuesOfMonth
{
	private int balance;
	private double averageBalance;
	private final LocalDate dateOfMonth;
	private final List<Transaction> transactionsOfMonth;
	private final int precedingBalance;

	public ValuesOfMonth()
	{
		this(new LocalDate(), new ArrayList<Transaction>(), 0);
	}

	public ValuesOfMonth(LocalDate dateOfMonth, List<Transaction> transactionsOfMonth, int precedingBalance)
	{
		super();
		this.dateOfMonth = dateOfMonth;
		this.transactionsOfMonth = transactionsOfMonth;
		this.precedingBalance = precedingBalance;
	}

	public int getBalance()
	{
		calculateValues();
		return balance;
	}

	public int getAverageBalance()
	{
		calculateValues();
		return (int) averageBalance;
	}

	public void setBalanceAndAverage(int balance, double averageBalance)
	{
		this.balance = balance;
		this.averageBalance = averageBalance;
	}

	private void calculateValues()
	{
		int balance = precedingBalance;
		int ultimo = dateOfMonth.getDayOfMonth();

		double averageBalance = 0;
		int dayOfLatestBalance = 1;
		for (Transaction transaction : transactionsOfMonth)
		{
			int day = transaction.getDate().getDayOfMonth();
			averageBalance += calculateProportionalBalance(dayOfLatestBalance, balance, day, ultimo);
			balance += transaction.getAmount();
			dayOfLatestBalance = day;
		}

		averageBalance += calculateProportionalBalance(dayOfLatestBalance, balance, ultimo + 1, ultimo);

		setBalanceAndAverage(balance, averageBalance);
	}

	private double calculateProportionalBalance(int dayOfLatestBalance, int balance, int day, int daysInMonth)
	{
		int countingDays = day - dayOfLatestBalance;
		if (countingDays == 0)
		{
			return 0;
		}
		double rate = (double) countingDays / daysInMonth;
		return (balance * rate);
	}

}
