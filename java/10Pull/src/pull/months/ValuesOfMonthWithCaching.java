package pull.months;

import java.util.List;

import org.joda.time.LocalDate;

import common.Transaction_API;

public class ValuesOfMonthWithCaching extends ValuesOfMonth {

	private Integer balanceLazy = null;
	private Integer averageBalanceLazy = null;

	public ValuesOfMonthWithCaching(LocalDate date, IValuesOfMonth month, List<Transaction_API> filteredTransactions) {
		super(date, month, filteredTransactions);
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
