package pushpull;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;

import common.Transaction_API;

public class ValuesOfMonth implements IValuesOfMonth {
	private final LocalDate dateOfMonth;
	private final List<Transaction_API> transactionsOfMonth;
	private final IValuesOfMonth precedingMonth;

	public ValuesOfMonth(IValuesOfMonth precedingMonth, LocalDate dateOfMonth,
			List<Transaction_API> transactionsOfMonth) {
		this.dateOfMonth = dateOfMonth;
		this.transactionsOfMonth = transactionsOfMonth;
		this.precedingMonth = precedingMonth;
	}

	@Override
	public int getBalance() {
		int balance = precedingMonth.getBalance();
		for (Transaction_API transaction : transactionsOfMonth) {
			balance += transaction.getAmount();
		}
		return balance;
	}

	public int getAverageBalance() {
		int result = precedingMonth.getBalance();
		for (Transaction_API transaction : transactionsOfMonth) {
			result += calculateProportion(transaction);
		}
		return result;
	}

	private double calculateProportion(Transaction_API transaction) {
		int coveredDays = daysInMonth() - transaction.getDate().getDayOfMonth() + 1;
		double rate = (double) coveredDays / daysInMonth();
		return (transaction.getAmount() * rate);
	}

	private int daysInMonth() {
		return ISOChronology.getInstance().dayOfMonth().getMaximumValue(dateOfMonth);
	}

}
