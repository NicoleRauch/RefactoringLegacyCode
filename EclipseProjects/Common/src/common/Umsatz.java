package common;

import org.joda.time.LocalDate;

public class Umsatz {

	private final LocalDate date;
	private final int umsatz;

	public Umsatz(LocalDate date, int umsatz) {
		super();
		this.date = date;
		this.umsatz = umsatz;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getUmsatz() {
		return umsatz;
	}

}
