package push;

import org.junit.Before;

import common.CalculatorTest;

public class PushTest extends CalculatorTest {

	@Before
	public void setup() {
		calculator = new PushingBalancesCalculator(transactions);
	}

}
