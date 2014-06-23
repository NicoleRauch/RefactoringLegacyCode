class ValuesOfMonth
  attr_reader :balance, :average_balance

  def set_balance_and_average(balance, average_balance)
    @balance = balance
    @average_balance = average_balance
  end
end
