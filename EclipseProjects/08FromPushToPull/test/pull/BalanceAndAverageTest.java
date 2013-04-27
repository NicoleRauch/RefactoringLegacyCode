package pull;

import java.util.ArrayList;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import common.Transaction;

public class BalanceAndAverageTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void monthWithoutTransactionsHasZeroBalance()
	{
		BalanceAndAverage balanceAndAverage = new BalanceAndAverage(APRIL_ULTIMO, new ArrayList<Transaction>(), 0);
		Assert.assertEquals(0, balanceAndAverage.getBalance());
		Assert.assertEquals(0, balanceAndAverage.getAverageBalance());
	}

	@Test
	public void monthWithOneTransactionHasBalanceOfThat()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 1), 100));

		BalanceAndAverage balanceAndAverage = new BalanceAndAverage(APRIL_ULTIMO, transactionsOfMonth, 0);
		Assert.assertEquals(100, balanceAndAverage.getBalance());
		Assert.assertEquals(100, balanceAndAverage.getAverageBalance());
	}

	@Test
	public void monthWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 1), 100));
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 16), 100));

		BalanceAndAverage balanceAndAverage = new BalanceAndAverage(APRIL_ULTIMO, transactionsOfMonth, 0);
		Assert.assertEquals(200, balanceAndAverage.getBalance());
		Assert.assertEquals(150, balanceAndAverage.getAverageBalance());
	}

	@Test
	public void twoMonthsWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 16), 100));
		BalanceAndAverage april = new BalanceAndAverage(APRIL_ULTIMO, transactionsOfMonth, 0);

		transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 5, 16), 200));
		BalanceAndAverage may = new BalanceAndAverage(MAY_ULTIMO, transactionsOfMonth, april.getBalance());

		Assert.assertEquals(300, may.getBalance());
		Assert.assertEquals(203, may.getAverageBalance());
	}
}
