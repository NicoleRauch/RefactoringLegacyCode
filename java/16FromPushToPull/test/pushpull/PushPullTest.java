package pushpull;

import org.junit.Before;

import common.CalculatorTest;
import pushpull.BalancesCalculator;

public class PushPullTest extends CalculatorTest {

	@Before
	public void setup() {
		calculator = new BalancesCalculator(transactions);
	}

}
