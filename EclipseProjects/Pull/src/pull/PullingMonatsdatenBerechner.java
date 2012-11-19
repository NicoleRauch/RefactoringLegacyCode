package pull;

import java.util.List;

import pull.monate.Monat;
import pull.monate.Monate;

import common.Monatsdaten;
import common.MonatsdatenBerechner;
import common.Umsatz;

public class PullingMonatsdatenBerechner implements MonatsdatenBerechner
{

	private final List<Umsatz> umsaetze;

	public PullingMonatsdatenBerechner(List<Umsatz> umsaetze)
	{
		super();
		this.umsaetze = umsaetze;
	}

	@Override
	public void fillData(List<Monatsdaten> monatsdatenliste)
	{
		Monate monate = new Monate(monatsdatenliste, umsaetze);

		for (Monatsdaten monatsdaten : monatsdatenliste)
		{
			Monat passenderMonat = monate.forDate(monatsdaten.getDate());
			monatsdaten.setBestand(passenderMonat.getBestand());
			monatsdaten.setDurchschnittsBestand(passenderMonat.getDurchschnittsBestand());
		}
	}
}
