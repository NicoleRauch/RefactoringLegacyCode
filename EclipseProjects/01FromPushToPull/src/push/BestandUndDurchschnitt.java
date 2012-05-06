package push;

public class BestandUndDurchschnitt
{
	private int bestand;
	private double durchschnittsBestand;

	public BestandUndDurchschnitt()
	{
		super();
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
}
