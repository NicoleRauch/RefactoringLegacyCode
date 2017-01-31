package push;

import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonth {
	private int balance;
	private double averageBalance;

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

	void calculateValues(int precedingBalance, LocalDate dateOfMonth, List<Transaction_API> transactionsOfMonth) {
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
