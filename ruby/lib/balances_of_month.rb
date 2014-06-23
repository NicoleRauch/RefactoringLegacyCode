class BalancesOfMonth
  attr_accessor :balance, :average_balance
  attr_reader :date
  def initialize(date)
    @date = date
  end
end
