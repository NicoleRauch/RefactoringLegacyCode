package pull;

import org.junit.Before;

import pull.PullingBalancesCalculator;

import common.CalculatorTest;

public class PullTest extends CalculatorTest {

	@Before
	public void setup() {
		calculator = new PullingBalancesCalculator(transactions);
	}

}
