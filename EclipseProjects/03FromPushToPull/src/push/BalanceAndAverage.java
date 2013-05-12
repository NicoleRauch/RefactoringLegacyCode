package push;

public class BalanceAndAverage
{
	private int balance;
	private double averageBalance;

	public BalanceAndAverage()
	{
		super();
	}

	public int getBalance()
	{
		return balance;
	}

	public int getAverageBalance()
	{
		return (int) averageBalance;
	}

	public void setBalanceAndAverage(int balance, double averageBalance)
	{
		this.balance = balance;
		this.averageBalance = averageBalance;
	}
}
