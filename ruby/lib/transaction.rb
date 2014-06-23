class Transaction
  attr_reader :date, :amount

  def initialize(date, amount)
    @date = date
    @amount = amount
  end
end
