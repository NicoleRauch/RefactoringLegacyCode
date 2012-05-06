package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Monatsdaten;
import common.MonatsdatenBerechner;
import common.Umsatz;

public class PushingMonatsdatenBerechner implements MonatsdatenBerechner
{

	private final List<Umsatz> umsaetze;

	public PushingMonatsdatenBerechner(List<Umsatz> umsaetze)
	{
		super();
		this.umsaetze = umsaetze;
	}

	@Override
	public void fillData(List<Monatsdaten> monatsdatenliste)
	{
		BestandUndDurchschnitt bestandUndDurchschnitt = new BestandUndDurchschnitt();

		for (Monatsdaten monatsdaten : monatsdatenliste)
		{
			LocalDate monatsDatum = monatsdaten.getDate();
			List<Umsatz> umsaetzeFuerMonat = umsaetzeFuerMonat(monatsDatum); // this method works on all items and stays in
																																				// this place

			berechneWerteFuerMonat(monatsDatum, umsaetzeFuerMonat, bestandUndDurchschnitt);

			monatsdaten.setBestand(bestandUndDurchschnitt.getBestand());
			monatsdaten.setDurchschnittsBestand(bestandUndDurchschnitt.getDurchschnittsBestand());
		}
	}

	/**
	 * extracted body of for loop in {@link #fillData(List)}
	 */
	private void berechneWerteFuerMonat(LocalDate monatsDatum, List<Umsatz> umsaetzeFuerMonat,
			BestandUndDurchschnitt bestandUndDurchschnitt)
	{
		int bestand = bestandUndDurchschnitt.getBestand();
		int letzterBestand = bestand;
		int ultimo = monatsDatum.getDayOfMonth();

		double durchschnittsBestand = 0;
		int tagDesLetztenBestands = 1;
		for (Umsatz umsatz : umsaetzeFuerMonat)
		{
			bestand += umsatz.getUmsatz();
			int tag = umsatz.getDate().getDayOfMonth();
			durchschnittsBestand += ermittleAnteiligenBestand(tagDesLetztenBestands, letzterBestand, tag, ultimo);
			letzterBestand = bestand;
			tagDesLetztenBestands = tag;
		}

		if (tagDesLetztenBestands != ultimo)
		{
			durchschnittsBestand += ermittleAnteiligenBestand(tagDesLetztenBestands, bestand, ultimo + 1, ultimo);
		}

		bestandUndDurchschnitt.setBestandUndDurchschnitt(bestand, durchschnittsBestand);
	}

	private double ermittleAnteiligenBestand(int tagDesLetztenBestands, int bestand, int tag, int anzahlTageImMonat)
	{
		int gueltigeTage = tag - tagDesLetztenBestands;
		if (gueltigeTage == 0)
		{
			return 0;
		}
		double anteil = (double) gueltigeTage / anzahlTageImMonat;
		return (bestand * anteil);
	}

	private List<Umsatz> umsaetzeFuerMonat(LocalDate date)
	{
		List<Umsatz> results = new ArrayList<Umsatz>();
		for (Umsatz umsatz : umsaetze)
		{
			LocalDate umsatzDatum = umsatz.getDate();
			if (areSameMonthAndYear(date, umsatzDatum))
			{
				results.add(umsatz);
			}
		}
		return results;
	}

	private boolean areSameMonthAndYear(LocalDate date, LocalDate umsatzDatum)
	{
		return umsatzDatum.getMonthOfYear() == date.getMonthOfYear() && umsatzDatum.getYear() == date.getYear();
	}

}
