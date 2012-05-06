package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Umsatz;

public class BestandUndDurchschnitt
{
	private int bestand;
	private double durchschnittsBestand;
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
		return bestand;
	}

	public int getDurchschnittsBestand()
	{
		return (int) durchschnittsBestand;
	}

	public void setBestandUndDurchschnitt(int bestand, double durchschnittsBestand)
	{
		this.bestand = bestand;
		this.durchschnittsBestand = durchschnittsBestand;
	}

	void berechneWerte()
	{
		int bestand = vorgaengerBestand;
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

		setBestandUndDurchschnitt(bestand, durchschnittsBestand);
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

}
