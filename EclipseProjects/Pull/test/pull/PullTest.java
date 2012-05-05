package pull;

import org.junit.Before;

import common.BerechnerTest;

public class PullTest extends BerechnerTest
{

	@Before
	public void setup()
	{
		berechner = new PullingMonatsdatenBerechner(umsaetze);
	}

}
