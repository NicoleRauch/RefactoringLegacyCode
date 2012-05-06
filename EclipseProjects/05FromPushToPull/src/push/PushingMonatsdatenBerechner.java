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
			List<Umsatz> umsaetzeFuerMonat = umsaetzeFuerMonat(monatsDatum);

			int vorgaengerBestand = bestandUndDurchschnitt.getBestand();

			bestandUndDurchschnitt = new BestandUndDurchschnitt(monatsDatum, umsaetzeFuerMonat, vorgaengerBestand);
			bestandUndDurchschnitt.berechneWerte();

			monatsdaten.setBestand(bestandUndDurchschnitt.getBestand());
			monatsdaten.setDurchschnittsBestand(bestandUndDurchschnitt.getDurchschnittsBestand());
		}
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
