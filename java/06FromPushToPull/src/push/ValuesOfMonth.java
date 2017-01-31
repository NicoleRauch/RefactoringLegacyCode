package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonth {
	private int balance;
	private double averageBalance;
	private final LocalDate dateOfMonth;
	private final List<Transaction_API> transactionsOfMonth;
	private final int precedingBalance;

	public ValuesOfMonth() {
		this(0, new LocalDate(), new ArrayList<Transaction_API>());
	}

	public ValuesOfMonth(int precedingBalance, LocalDate dateOfMonth, List<Transaction_API> transactionsOfMonth) {
		this.dateOfMonth = dateOfMonth;
		this.transactionsOfMonth = transactionsOfMonth;
		this.precedingBalance = precedingBalance;
	}

	public int getBalance() {
		return balance;
	}

	public int getAverageBalance() {
		return (int) averageBalance;
	}

	public void setBalanceAndAverage(int balance, double averageBalance) {
		this.balance = balance;
		this.averageBalance = averageBalance;
	}

	void calculateValues() {
		int balance = precedingBalance;
		int ultimo = dateOfMonth.getDayOfMonth();

		double averageBalance = 0;
		int dayOfLatestBalance = 1;
		for (Transaction_API transaction : transactionsOfMonth) {
			int day = transaction.getDate().getDayOfMonth();
			averageBalance += calculateProportionalBalance(dayOfLatestBalance, balance, day, ultimo);
			balance += transaction.getAmount();
			dayOfLatestBalance = day;
		}

		averageBalance += calculateProportionalBalance(dayOfLatestBalance, balance, ultimo + 1, ultimo);

		setBalanceAndAverage(balance, averageBalance);
	}

	private double calculateProportionalBalance(int dayOfLatestBalance, int balance, int day, int daysInMonth) {
		int countingDays = day - dayOfLatestBalance;
		if (countingDays == 0) {
			return 0;
		}
		double rate = (double) countingDays / daysInMonth;
		return (balance * rate);
	}

}
