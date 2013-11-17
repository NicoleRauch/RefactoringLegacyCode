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
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();
		int balance = 0;

		for (BalancesOfMonth balancesOfMonth : balancesOfMonthList)
		{
			LocalDate dateOfMonth = balancesOfMonth.getDate();
			List<Transaction> transactionsOfMonth = transactionsOfMonth(dateOfMonth);

			valuesOfMonth = calculateValuesForMonth(balance, dateOfMonth, transactionsOfMonth);
			balance = valuesOfMonth.getBalance();

			balancesOfMonth.setBalance(valuesOfMonth.getBalance());
			balancesOfMonth.setAverageBalance(valuesOfMonth.getAverageBalance());
		}
	}

	private ValuesOfMonth calculateValuesForMonth(int precedingBalance, LocalDate dateOfMonth, List<Transaction> transactionsOfMonth)
	{
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth();

		valuesOfMonth.calculateValues(dateOfMonth, transactionsOfMonth, precedingBalance);
		return valuesOfMonth;
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
