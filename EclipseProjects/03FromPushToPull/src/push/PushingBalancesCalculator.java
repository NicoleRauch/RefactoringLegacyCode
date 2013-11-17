package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.BalancesOfMonth;
import common.BalancesOfMonthCalculator;
import common.Transaction;

public class PushingBalancesCalculator implements BalancesOfMonthCalculator
{

	private final List<Transaction> transactions;

	public PushingBalancesCalculator(List<Transaction> transactions)
	{
		super();
		this.transactions = transactions;
	}

	@Override
	public void fillData(List<BalancesOfMonth> balancesOfMonthList)
	{
		ValuesOfMonth balanceAndAverage = new ValuesOfMonth();

		for (BalancesOfMonth balancesOfMonth : balancesOfMonthList)
		{
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction> transactionsOfMonth = transactionsOfMonth(dateOfMonth);

			int balance = balanceAndAverage.getBalance();
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

			balanceAndAverage.setBalanceAndAverage(balance, averageBalance);

			balancesOfMonth.setBalance(balanceAndAverage.getBalance());
			balancesOfMonth.setAverageBalance(balanceAndAverage.getAverageBalance());
		}
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

	private List<Transaction> transactionsOfMonth(LocalDate date)
	{
		List<Transaction> results = new ArrayList<Transaction>();
		for (Transaction transaction : transactions)
		{
			LocalDate dateOfTransaction = transaction.getDate();
			if (areSameMonthAndYear(date, dateOfTransaction))
			{
				results.add(transaction);
			}
		}
		return results;
	}

	private boolean areSameMonthAndYear(LocalDate date, LocalDate dateOfTransaction)
	{
		return dateOfTransaction.getMonthOfYear() == date.getMonthOfYear() && dateOfTransaction.getYear() == date.getYear();
	}

}
