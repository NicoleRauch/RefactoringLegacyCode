package push;

import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction;

public class BalanceAndAverage
{
	private int balance;
	private double averageBalance;

	public BalanceAndAverage()
	{
		super();
	}

	public int getBalance()
	{
		return balance;
	}

	public int getAverageBalance()
	{
		return (int) averageBalance;
	}

	public void setBalanceAndAverage(int balance, double averageBalance)
	{
		this.balance = balance;
		this.averageBalance = averageBalance;
	}

	void calculateValues(LocalDate dateOfMonth, List<Transaction> transactionsOfMonth, int precedingBalance)
	{
		int balance = precedingBalance;
		int latestBalance = balance;
		int ultimo = dateOfMonth.getDayOfMonth();

		double averageBalance = 0;
		int dayOfLatestBalance = 1;
		for (Transaction transaction : transactionsOfMonth)
		{
			balance += transaction.getAmount();
			int day = transaction.getDate().getDayOfMonth();
			averageBalance += calculateProportionalBalance(dayOfLatestBalance, latestBalance, day, ultimo);
			latestBalance = balance;
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
