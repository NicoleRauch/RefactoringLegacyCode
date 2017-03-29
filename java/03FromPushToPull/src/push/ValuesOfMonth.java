package push;

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
}
