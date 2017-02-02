package pull.months;

import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import common.Transaction_API;

public class MonthTest {
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void monthWithoutTransactionsHasZeroBalance() {
		ValuesOfMonth month = new ValuesOfMonth(APRIL_ULTIMO, new InitialValuesOfMonth(), list());
		Assert.assertEquals(0, month.getBalance());
		Assert.assertEquals(0, month.getAverageBalance());
	}

	@Test
	public void monthWithOneTransactionHasBalanceOfThat() {
		ValuesOfMonth month = new ValuesOfMonth(APRIL_ULTIMO, new InitialValuesOfMonth(),
				list(new Transaction_API(new LocalDate(2010, 4, 1), 100)));
		Assert.assertEquals(100, month.getBalance());
		Assert.assertEquals(100, month.getAverageBalance());
	}
	@Test
	public void monthWithTwoTransactionsHasSumOfThoseAsBalance() {
		ValuesOfMonth month = new ValuesOfMonth(APRIL_ULTIMO, new InitialValuesOfMonth(), list(
				new Transaction_API(new LocalDate(2010, 4, 1), 100), new Transaction_API(new LocalDate(2010, 4, 16), 100)));
		Assert.assertEquals(200, month.getBalance());
		Assert.assertEquals(150, month.getAverageBalance());
	}

	@Test
	public void twoMonthsWithTwoTransactionsHasSumOfThoseAsBalance() {
		ValuesOfMonth april = new ValuesOfMonth(APRIL_ULTIMO, new InitialValuesOfMonth(),
				list(new Transaction_API(new LocalDate(2010, 4, 16), 100)));

		ValuesOfMonth may = new ValuesOfMonth(MAY_ULTIMO, april,
				list(new Transaction_API(new LocalDate(2010, 5, 16), 200)));
		Assert.assertEquals(300, may.getBalance());
		Assert.assertEquals(203, may.getAverageBalance());
	}

	private List<Transaction_API> list(Transaction_API... entries) {
		return Arrays.asList(entries);
	}
}
