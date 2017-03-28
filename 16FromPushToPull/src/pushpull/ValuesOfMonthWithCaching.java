package pushpull;

import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonthWithCaching extends ValuesOfMonth {

	private Integer balanceLazy = null;
	private Integer averageBalanceLazy = null;

	public ValuesOfMonthWithCaching(IValuesOfMonth precedingMonth, LocalDate dateOfMonth,
			List<Transaction_API> transactionsOfMonth) {
		super(precedingMonth, dateOfMonth, transactionsOfMonth);
	}

	@Override
	public int getBalance() {
		if (balanceLazy == null) {
			balanceLazy = Integer.valueOf(super.getBalance());
		}
		return balanceLazy.intValue();
	}

	@Override
	public int getAverageBalance() {
		if (averageBalanceLazy == null) {
			averageBalanceLazy = Integer.valueOf(super.getAverageBalance());
		}
		return averageBalanceLazy.intValue();
	}
}
