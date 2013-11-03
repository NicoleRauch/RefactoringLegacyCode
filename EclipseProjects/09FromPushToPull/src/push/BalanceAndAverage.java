package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction;

public class BalanceAndAverage
{
	private int balance;
	private double averageBalance;
	private final LocalDate dateOfMonth;
	private final List<Transaction> transactionsOfMonth;
	private final int precedingBalance;

	public BalanceAndAverage()
	{
		this(new LocalDate(), new ArrayList<Transaction>(), 0);
	}

	public BalanceAndAverage(LocalDate dateOfMonth, List<Transaction> transactionsOfMonth, int precedingBalance)
	{
		super();
		this.dateOfMonth = dateOfMonth;
		this.transactionsOfMonth = transactionsOfMonth;
		this.precedingBalance = precedingBalance;
	}

	public int getBalance()
	{
		return balance;
	}

	public int getAverageBalance()
	{
		return (int) averageBalance;
	}

	void calculateValues()
	{
		balance = calculateBalance();
		averageBalance = calculateAverageBalance();
	}

	private int calculateBalance()
	{
		int balance = precedingBalance;
		for (Transaction inputData : transactionsOfMonth)
		{
			balance += inputData.getAmount();
		}
		return balance;
	}

	private int calculateAverageBalance()
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

		return (int) averageBalance;
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
