package pull;

import java.util.ArrayList;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import common.Transaction;

public class ValuesOfMonthTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void monthWithoutTransactionsHasZeroBalance()
	{
		ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, new ArrayList<Transaction>(), 0);
		Assert.assertEquals(0, valuesOfMonth.getBalance());
		Assert.assertEquals(0, valuesOfMonth.getAverageBalance());
	}

	@Test
	public void monthWithOneTransactionHasBalanceOfThat()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 1), 100));

		ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, transactionsOfMonth, 0);
		Assert.assertEquals(100, valuesOfMonth.getBalance());
		Assert.assertEquals(100, valuesOfMonth.getAverageBalance());
	}

	@Test
	public void monthWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 1), 100));
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 16), 100));

		ValuesOfMonth valuesOfMonth = new ValuesOfMonth(APRIL_ULTIMO, transactionsOfMonth, 0);
		Assert.assertEquals(200, valuesOfMonth.getBalance());
		Assert.assertEquals(150, valuesOfMonth.getAverageBalance());
	}

	@Test
	public void twoMonthsWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		ArrayList<Transaction> transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 4, 16), 100));
		ValuesOfMonth april = new ValuesOfMonth(APRIL_ULTIMO, transactionsOfMonth, 0);

		transactionsOfMonth = new ArrayList<Transaction>();
		transactionsOfMonth.add(new Transaction(new LocalDate(2010, 5, 16), 200));
		ValuesOfMonth may = new ValuesOfMonth(MAY_ULTIMO, transactionsOfMonth, april.getBalance());

		Assert.assertEquals(300, may.getBalance());
		Assert.assertEquals(203, may.getAverageBalance());
	}
}
