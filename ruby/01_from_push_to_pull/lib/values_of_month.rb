class ValuesOfMonth
  attr_reader :balance

  def set_balance_and_average(balance, average_balance)
    @balance = balance
    @average_balance = average_balance
  end

  def average_balance
    Integer(@average_balance)
  end
end
