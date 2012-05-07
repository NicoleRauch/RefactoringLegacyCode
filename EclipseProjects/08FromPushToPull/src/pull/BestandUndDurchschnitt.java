package pull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Umsatz;

public class BestandUndDurchschnitt
{
	private final LocalDate monatsDatum;
	private final List<Umsatz> umsaetzeFuerMonat;
	private final int vorgaengerBestand;

	public BestandUndDurchschnitt()
	{
		this(new LocalDate(), new ArrayList<Umsatz>(), 0);
	}

	public BestandUndDurchschnitt(LocalDate monatsDatum, List<Umsatz> umsaetzeFuerMonat, int vorgaengerBestand)
	{
		super();
		this.monatsDatum = monatsDatum;
		this.umsaetzeFuerMonat = umsaetzeFuerMonat;
		this.vorgaengerBestand = vorgaengerBestand;
	}

	public int getBestand()
	{
		int bestand = vorgaengerBestand;
		for (Umsatz inputData : umsaetzeFuerMonat)
		{
			bestand += inputData.getUmsatz();
		}
		return bestand;
	}

	public int getDurchschnittsBestand()
	{
		double durchschnittsBestand = vorgaengerBestand;
		for (Umsatz umsatz : umsaetzeFuerMonat)
		{
			durchschnittsBestand += anteilFuer(umsatz);
		}
		return (int) durchschnittsBestand;
	}

	private double anteilFuer(Umsatz umsatz)
	{
		int anzahlTageDesMonats = monatsDatum.getDayOfMonth();
		int gueltigeTage = anzahlTageDesMonats - umsatz.getDate().getDayOfMonth() + 1;
		double anteil = (double) gueltigeTage / anzahlTageDesMonats;
		return (umsatz.getUmsatz() * anteil);
	}

}
