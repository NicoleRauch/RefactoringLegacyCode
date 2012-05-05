package pull.monate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import common.Monatsdaten;
import common.Umsatz;

public class Monate
{

	private final List<Monat> monate = new ArrayList<Monat>();
	private final Map<YearMonth, Monat> monateInMap = new HashMap<YearMonth, Monat>();

	public Monate(List<Monatsdaten> monateEinesKontos, List<Umsatz> umsaetze)
	{
		super();
		init(monateEinesKontos, umsaetze);
	}

	private void init(List<Monatsdaten> monateEinesKontos, List<Umsatz> umsaetze)
	{
		Monat monat = null;
		for (Monatsdaten monatEinesKontos : monateEinesKontos)
		{
			monat = new MonatMitCaching(monatEinesKontos.getDate(), monat != null ? monat : new DummyMonat());
			getMonate().add(monat);
			monateInMap.put(monat.getYearMonth(), monat);
		}

		verteileUmsaetzeAufMonate(umsaetze);
	}

	public List<Monat> getMonate()
	{
		return monate;
	}

	public Monat forDate(LocalDate date)
	{
		return monateInMap.get(new YearMonth(date));
	}

	private void verteileUmsaetzeAufMonate(List<Umsatz> umsaetze)
	{
		for (Umsatz umsatz : umsaetze)
		{
			forDate(umsatz.getDate()).addUmsatz(umsatz);
		}
	}

}
