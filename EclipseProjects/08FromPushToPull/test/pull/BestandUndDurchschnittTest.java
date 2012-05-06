package pull;

import java.util.ArrayList;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;

import pull.BestandUndDurchschnitt;

import common.Umsatz;

public class BestandUndDurchschnittTest
{
	private static final LocalDate APRIL_ULTIMO = new LocalDate(2010, 4, 30);
	private static final LocalDate MAI_ULTIMO = new LocalDate(2010, 5, 31);

	@Test
	public void einMonatOhneUmsaetzeHatKeinenBestand()
	{
		BestandUndDurchschnitt bestandUndDurchschnitt = new BestandUndDurchschnitt(APRIL_ULTIMO, new ArrayList<Umsatz>(), 0);
		Assert.assertEquals(0, bestandUndDurchschnitt.getBestand());
		Assert.assertEquals(0, bestandUndDurchschnitt.getDurchschnittsBestand());
	}

	@Test
	public void einMonatMitEinemUmsatzHatDessenUmsatzAlsBestand()
	{
		ArrayList<Umsatz> umsaetzeFuerMonat = new ArrayList<Umsatz>();
		umsaetzeFuerMonat.add(new Umsatz(new LocalDate(2010, 4, 1), 100));

		BestandUndDurchschnitt bestandUndDurchschnitt = new BestandUndDurchschnitt(APRIL_ULTIMO, umsaetzeFuerMonat, 0);
		Assert.assertEquals(100, bestandUndDurchschnitt.getBestand());
		Assert.assertEquals(100, bestandUndDurchschnitt.getDurchschnittsBestand());
	}

	@Test
	public void einMonatMitZweiUmsaetzenHatDieSummeAlsBestand()
	{
		ArrayList<Umsatz> umsaetzeFuerMonat = new ArrayList<Umsatz>();
		umsaetzeFuerMonat.add(new Umsatz(new LocalDate(2010, 4, 1), 100));
		umsaetzeFuerMonat.add(new Umsatz(new LocalDate(2010, 4, 16), 100));

		BestandUndDurchschnitt bestandUndDurchschnitt = new BestandUndDurchschnitt(APRIL_ULTIMO, umsaetzeFuerMonat, 0);
		Assert.assertEquals(200, bestandUndDurchschnitt.getBestand());
		Assert.assertEquals(150, bestandUndDurchschnitt.getDurchschnittsBestand());
	}

	@Test
	public void zweiMonateMitZweiUmsaetzenHatDieSummeAlsBestand()
	{
		ArrayList<Umsatz> umsaetzeFuerMonat = new ArrayList<Umsatz>();
		umsaetzeFuerMonat.add(new Umsatz(new LocalDate(2010, 4, 16), 100));
		BestandUndDurchschnitt april = new BestandUndDurchschnitt(APRIL_ULTIMO, umsaetzeFuerMonat, 0);

		umsaetzeFuerMonat = new ArrayList<Umsatz>();
		umsaetzeFuerMonat.add(new Umsatz(new LocalDate(2010, 5, 16), 200));
		BestandUndDurchschnitt mai = new BestandUndDurchschnitt(MAI_ULTIMO, umsaetzeFuerMonat, april.getBestand());

		Assert.assertEquals(300, mai.getBestand());
		Assert.assertEquals(203, mai.getDurchschnittsBestand());
	}
}
