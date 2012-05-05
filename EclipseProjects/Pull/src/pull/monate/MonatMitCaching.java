package pull.monate;

import org.joda.time.LocalDate;

public class MonatMitCaching extends Monat
{

	private Integer bestandLazy = null;
	private Integer durchschnittsBestandLazy = null;

	public MonatMitCaching(LocalDate date, IMonat monat)
	{
		super(date, monat);
	}

	@Override
	public int getBestand()
	{
		if (bestandLazy == null)
		{
			bestandLazy = Integer.valueOf(super.getBestand());
		}
		return bestandLazy.intValue();
	}

	@Override
	public int getDurschschnittsBestand()
	{
		if (durchschnittsBestandLazy == null)
		{
			durchschnittsBestandLazy = Integer.valueOf(super.getDurschschnittsBestand());
		}
		return durchschnittsBestandLazy.intValue();
	}
}
