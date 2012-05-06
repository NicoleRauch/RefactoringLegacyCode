package push;

import org.junit.Before;

import common.BerechnerTest;

public class PushTest extends BerechnerTest
{

	@Before
	public void setup()
	{
		berechner = new PushingMonatsdatenBerechner(umsaetze);
	}

}
