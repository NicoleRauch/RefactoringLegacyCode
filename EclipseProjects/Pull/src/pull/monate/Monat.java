package pull.monate;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.chrono.ISOChronology;

import common.Umsatz;

public class Monat implements IMonat
{

	protected final YearMonth yearMonth;
	protected final IMonat vorgaengerMonat;
	protected final List<Umsatz> umsaetze = new ArrayList<Umsatz>();

	public Monat(LocalDate date, IMonat monat)
	{
		super();
		this.vorgaengerMonat = monat;
		this.yearMonth = new YearMonth(date);
	}

	public YearMonth getYearMonth()
	{
		return yearMonth;
	}

	public void addUmsatz(Umsatz umsatz)
	{
		umsaetze.add(umsatz);
	}

	@Override
	public int getBestand()
	{
		int result = vorgaengerMonat.getBestand();
		for (Umsatz umsatz : umsaetze)
		{
			result += umsatz.getUmsatz();
		}
		return result;
	}

	public int getDurchschnittsBestand()
	{
		int result = vorgaengerMonat.getBestand();
		for (Umsatz umsatz : umsaetze)
		{
			result += ermittleAnteil(umsatz);
		}

		return result;
	}

	private double ermittleAnteil(Umsatz umsatz)
	{
		int gueltigeTage = anzahlTageImMonat() - umsatz.getDate().getDayOfMonth() + 1;
		double anteil = (double) gueltigeTage / anzahlTageImMonat();
		return (umsatz.getUmsatz() * anteil);
	}

	private int anzahlTageImMonat()
	{
		return ISOChronology.getInstance().dayOfMonth().getMaximumValue(yearMonth);
	}

}
