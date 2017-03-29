package push;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonth {
	private final LocalDate dateOfMonth;
	private final List<Transaction_API> transactionsOfMonth;
	private final ValuesOfMonth precedingMonth;

	public ValuesOfMonth() {
		this(null, new LocalDate(), new ArrayList<Transaction_API>());
	}

	public ValuesOfMonth(ValuesOfMonth precedingMonth, LocalDate dateOfMonth, List<Transaction_API> transactionsOfMonth) {
		this.dateOfMonth = dateOfMonth;
		this.transactionsOfMonth = transactionsOfMonth;
		this.precedingMonth = precedingMonth;
	}

	public int getBalance() {
		int balance = precedingMonth == null ? 0 : precedingMonth.getBalance();
		for (Transaction_API transaction : transactionsOfMonth) {
			balance += transaction.getAmount();
		}
		return balance;
	}

	public int getAverageBalance() {
		int balance = precedingMonth == null ? 0 : precedingMonth.getBalance();
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

		return (int) averageBalance;
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
