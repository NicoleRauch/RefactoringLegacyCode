package pull.months;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import common.Transaction;

public class MonthTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void monthWithoutTransactionsHasZeroBalance()
	{
		Month month = new Month(APRIL_ULTIMO, new DummyMonth());
		Assert.assertEquals(0, month.getBalance());
		Assert.assertEquals(0, month.getAverageBalance());
	}

	@Test
	public void monthWithOneTransactionHasBalanceOfThat()
	{
		Month month = new Month(APRIL_ULTIMO, new DummyMonth());
		month.addTransaction(new Transaction(new LocalDate(2010, 4, 1), 100));
		Assert.assertEquals(100, month.getBalance());
		Assert.assertEquals(100, month.getAverageBalance());
	}
	@Test
	public void monthWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		Month month = new Month(APRIL_ULTIMO, new DummyMonth());
		month.addTransaction(new Transaction(new LocalDate(2010, 4, 1), 100));
		month.addTransaction(new Transaction(new LocalDate(2010, 4, 16), 100));
		Assert.assertEquals(200, month.getBalance());
		Assert.assertEquals(150, month.getAverageBalance());
	}

	@Test
	public void twoMonthsWithTwoTransactionsHasSumOfThoseAsBalance()
	{
		Month april = new Month(APRIL_ULTIMO, new DummyMonth());
		april.addTransaction(new Transaction(new LocalDate(2010, 4, 16), 100));

		Month may = new Month(MAY_ULTIMO, april);
		may.addTransaction(new Transaction(new LocalDate(2010, 5, 16), 200));
		Assert.assertEquals(300, may.getBalance());
		Assert.assertEquals(203, may.getAverageBalance());
	}
}
