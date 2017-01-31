package pull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonth {
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
		int balance = precedingBalance;
		for (Transaction_API transaction : transactionsOfMonth) {
			balance += transaction.getAmount();
		}
		return balance;
	}

	public int getAverageBalance() {
		double averageBalance = precedingBalance;
		for (Transaction_API transaction : transactionsOfMonth) {
			averageBalance += rateOf(transaction);
		}
		return (int) averageBalance;
	}

	private double rateOf(Transaction_API transaction) {
		int daysOfMonth = dateOfMonth.getDayOfMonth();
		int countingDays = daysOfMonth - transaction.getDate().getDayOfMonth() + 1;
		double rate = (double) countingDays / daysOfMonth;
		return (transaction.getAmount() * rate);
	}

}
