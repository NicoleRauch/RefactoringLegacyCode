package common;

import org.joda.time.LocalDate;

public class Monatsdaten {

	private final LocalDate date;

	private int bestand;
	private int durchschnittsBestand;

	public Monatsdaten(LocalDate date) {
		super();
		this.date = date;
	}

	public int getBestand() {
		return bestand;
	}

	public int getDurchschnittsBestand() {
		return durchschnittsBestand;
	}

	public void setBestand(int bestand) {
		this.bestand = bestand;
	}

	public void setDurchschnittsBestand(int durchschnittsBestand) {
		this.durchschnittsBestand = durchschnittsBestand;
	}

	public LocalDate getDate() {
		return date;
	}

}
