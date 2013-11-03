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
public abstract class CalculatorTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAY_ULTIMO = new LocalDate(2010, 5, 31);
	private static final LocalDate JUNE_ULTIMO = new LocalDate(2010, 6, 30);
	private final List<BalancesOfMonth> balances = new ArrayList<BalancesOfMonth>();

	protected final List<Transaction> transactions = new ArrayList<Transaction>();
	protected BalancesOfMonthCalculator calculator;

	@Test
	public void oneTransactionAtFirstOfMonthSetsBalanceAndAverage()
	{
		transactions.add(new Transaction(new LocalDate(2010, 4, 1), 100));
		balances.add(new BalancesOfMonth(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth aprilData = balances.get(0);
		assertEquals(100, aprilData.getBalance());
		assertEquals(100, aprilData.getAverageBalance());
	}

	@Test
	public void oneTransactionAtUltimoOfMonthSetsBalanceAndAverage()
	{
		transactions.add(new Transaction(new LocalDate(2010, 4, 30), 300));
		balances.add(new BalancesOfMonth(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth aprilData = balances.get(0);
		assertEquals(300, aprilData.getBalance());
		assertEquals(10, aprilData.getAverageBalance());
	}

	@Test
	public void twoTransactionAtFirstAndSixteenthOfMonthLeadToHalfAverageOfBalance()
	{
		transactions.add(new Transaction(new LocalDate(2010, 4, 1), 100));
		transactions.add(new Transaction(new LocalDate(2010, 4, 16), 200));
		balances.add(new BalancesOfMonth(APRIL_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth aprilData = balances.get(0);
		assertEquals(300, aprilData.getBalance());
		assertEquals(200, aprilData.getAverageBalance());
	}

	@Test
	public void balanceOfPreviousMonthIsFactoredIn()
	{
		transactions.add(new Transaction(new LocalDate(2010, 4, 16), 100));
		transactions.add(new Transaction(new LocalDate(2010, 5, 16), 200));
		balances.add(new BalancesOfMonth(APRIL_ULTIMO));
		balances.add(new BalancesOfMonth(MAY_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth aprilData = balances.get(0);
		BalancesOfMonth mayData = balances.get(1);
		assertEquals(100, aprilData.getBalance());
		assertEquals(50, aprilData.getAverageBalance());
		assertEquals(300, mayData.getBalance());
		assertEquals(203, mayData.getAverageBalance());
	}

	@Test
	public void balanceOfPreviousMonthIsFactoredInOverTwoMonths()
	{
		transactions.add(new Transaction(new LocalDate(2010, 4, 16), 100));
		transactions.add(new Transaction(new LocalDate(2010, 5, 16), 200));
		balances.add(new BalancesOfMonth(APRIL_ULTIMO));
		balances.add(new BalancesOfMonth(MAY_ULTIMO));
		balances.add(new BalancesOfMonth(JUNE_ULTIMO));

		calculator.fillData(balances);

		BalancesOfMonth juneData = balances.get(2);
		assertEquals(300, juneData.getBalance());
		assertEquals(300, juneData.getAverageBalance());
	}
}
