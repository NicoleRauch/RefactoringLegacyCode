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
		int bestand = 0;

		int letzterBestand = 0;
		for (Monatsdaten monatsdaten : monatsdatenliste)
		{
			int ultimo = monatsdaten.getDate().getDayOfMonth();

			double durchschnittsBestand = 0;
			int tagDesLetztenBestands = 1;
			List<Umsatz> umsaetzeFuerMonat = umsaetzeFuerMonat(monatsdaten.getDate());
			for (Umsatz inputData : umsaetzeFuerMonat)
			{
				bestand += inputData.getUmsatz();
				int tag = inputData.getDate().getDayOfMonth();
				durchschnittsBestand += ermittleAnteiligenBestand(tagDesLetztenBestands, letzterBestand, tag, ultimo);
				letzterBestand = bestand;
				tagDesLetztenBestands = tag;
			}

			if (tagDesLetztenBestands != ultimo)
			{
				durchschnittsBestand += ermittleAnteiligenBestand(tagDesLetztenBestands, bestand, ultimo + 1, ultimo);
			}

			monatsdaten.setBestand(bestand);
			monatsdaten.setDurchschnittsBestand((int) durchschnittsBestand);
		}
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
