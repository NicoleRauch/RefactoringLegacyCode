package pull.months;

import org.joda.time.LocalDate;

public class ValuesOfMonthWithCaching extends ValuesOfMonth {

	private Integer balanceLazy = null;
	private Integer averageBalanceLazy = null;

	public ValuesOfMonthWithCaching(LocalDate date, IValuesOfMonth month) {
		super(date, month);
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
