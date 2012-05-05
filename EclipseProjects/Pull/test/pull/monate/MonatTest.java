package pull.monate;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import common.Umsatz;

public class MonatTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAI_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void einMonatOhneUmsaetzeHatKeinenBestand()
	{
		Monat monat = new Monat(APRIL_ULTIMO, new DummyMonat());
		Assert.assertEquals(0, monat.getBestand());
		Assert.assertEquals(0, monat.getDurschschnittsBestand());
	}

	@Test
	public void einMonatMitEinemUmsatzHatDessenUmsatzAlsBestand()
	{
		Monat monat = new Monat(APRIL_ULTIMO, new DummyMonat());
		monat.addUmsatz(new Umsatz(new LocalDate(2010, 4, 1), 100));
		Assert.assertEquals(100, monat.getBestand());
		Assert.assertEquals(100, monat.getDurschschnittsBestand());
	}
	@Test
	public void einMonatMitZweiUmsaetzenHatDieSummeAlsBestand()
	{
		Monat monat = new Monat(APRIL_ULTIMO, new DummyMonat());
		monat.addUmsatz(new Umsatz(new LocalDate(2010, 4, 1), 100));
		monat.addUmsatz(new Umsatz(new LocalDate(2010, 4, 16), 100));
		Assert.assertEquals(200, monat.getBestand());
		Assert.assertEquals(150, monat.getDurschschnittsBestand());
	}

	@Test
	public void zweiMonateMitZweiUmsaetzenHatDieSummeAlsBestand()
	{
		Monat april = new Monat(APRIL_ULTIMO, new DummyMonat());
		april.addUmsatz(new Umsatz(new LocalDate(2010, 4, 16), 100));

		Monat mai = new Monat(MAI_ULTIMO, april);
		mai.addUmsatz(new Umsatz(new LocalDate(2010, 5, 16), 200));
		Assert.assertEquals(300, mai.getBestand());
		Assert.assertEquals(203, mai.getDurschschnittsBestand());
	}
}
