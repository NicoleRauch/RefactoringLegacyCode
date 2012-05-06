package pull;

import org.junit.Before;

import pull.PullingMonatsdatenBerechner;

import common.BerechnerTest;

public class PushTest extends BerechnerTest
{

	@Before
	public void setup()
	{
		berechner = new PullingMonatsdatenBerechner(umsaetze);
	}

}
