package pull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction;

public class BalanceAndAverage
{
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
		int balance = precedingBalance;
		for (Transaction transaction : transactionsOfMonth)
		{
			balance += transaction.getAmount();
		}
		return balance;
	}

	public int getAverageBalance()
	{
		double averageBalance = precedingBalance;
		for (Transaction transaction : transactionsOfMonth)
		{
			averageBalance += rateOf(transaction);
		}
		return (int) averageBalance;
	}

	private double rateOf(Transaction transaction)
	{
		int daysOfMonth = dateOfMonth.getDayOfMonth();
		int countingDays = daysOfMonth - transaction.getDate().getDayOfMonth() + 1;
		double rate = (double) countingDays / daysOfMonth;
		return (transaction.getAmount() * rate);
	}

}
