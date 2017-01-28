package common;

import org.joda.time.LocalDate;

public class BalancesOfMonth_API {

	private final LocalDate date;

	private int balance;
	private int averageBalance;

	public BalancesOfMonth_API(LocalDate date) {
		super();
		this.date = date;
	}

	public int getBalance() {
		return balance;
	}

	public int getAverageBalance() {
		return averageBalance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public void setAverageBalance(int averageBalance) {
		this.averageBalance = averageBalance;
	}

	public LocalDate getDate() {
		return date;
	}

}
