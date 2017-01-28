package common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * <p>
 * This abstract class contains the integration tests for the demo code.
 * </p>
 * <p>
 * This is done to avoid code duplication as well as to insure that all the tests are testing the same.
 * </p>
 */
public abstract class CalculatorTest {
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);
	private static final LocalDate JUNE_ULTIMO = new LocalDate(2010, 6, 30);
	private final List<BalancesOfMonth_API> balances = new ArrayList<BalancesOfMonth_API>();

	protected final List<Transaction_API> transactions = new ArrayList<Transaction_API>();
	protected BalancesOfMonthCalculator_API calculator;

	@Test
	public void oneTransactionAtFirstOfMonthSetsBalanceAndAverage() {
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 1), 100));
		balances.add(new BalancesOfMonth_API(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth_API aprilData = balances.get(0);
		assertEquals(100, aprilData.getBalance());
		assertEquals(100, aprilData.getAverageBalance());
	}

	@Test
	public void oneTransactionAtUltimoOfMonthSetsBalanceAndAverage() {
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 30), 300));
		balances.add(new BalancesOfMonth_API(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth_API aprilData = balances.get(0);
		assertEquals(300, aprilData.getBalance());
		assertEquals(10, aprilData.getAverageBalance());
	}

	@Test
	public void twoTransactionAtFirstAndSixteenthOfMonthLeadToHalfAverageOfBalance() {
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 1), 100));
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 16), 200));
		balances.add(new BalancesOfMonth_API(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth_API aprilData = balances.get(0);
		assertEquals(300, aprilData.getBalance());
		assertEquals(200, aprilData.getAverageBalance());
	}

	@Test
	public void balanceOfPreviousMonthIsFactoredIn() {
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 16), 100));
		transactions.add(new Transaction_API(new LocalDate(2010, 5, 16), 200));
		balances.add(new BalancesOfMonth_API(APRIL_ULTIMO));
		balances.add(new BalancesOfMonth_API(MAY_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth_API aprilData = balances.get(0);
		BalancesOfMonth_API mayData = balances.get(1);
		assertEquals(100, aprilData.getBalance());
		assertEquals(50, aprilData.getAverageBalance());
		assertEquals(300, mayData.getBalance());
		assertEquals(203, mayData.getAverageBalance());
	}

	@Test
	public void balanceOfPreviousMonthIsFactoredInOverTwoMonths() {
		transactions.add(new Transaction_API(new LocalDate(2010, 4, 16), 100));
		transactions.add(new Transaction_API(new LocalDate(2010, 5, 16), 200));
		balances.add(new BalancesOfMonth_API(APRIL_ULTIMO));
		balances.add(new BalancesOfMonth_API(MAY_ULTIMO));
		balances.add(new BalancesOfMonth_API(JUNE_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth_API juneData = balances.get(2);
		assertEquals(300, juneData.getBalance());
		assertEquals(300, juneData.getAverageBalance());
	}
}
