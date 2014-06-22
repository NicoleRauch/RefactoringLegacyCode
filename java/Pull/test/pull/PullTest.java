package pull;

import org.junit.Before;

import common.CalculatorTest;

public class PullTest extends CalculatorTest
{

	@Before
	public void setup()
	{
		calculator = new PullingBalancesCalculator(transactions);
	}

}
