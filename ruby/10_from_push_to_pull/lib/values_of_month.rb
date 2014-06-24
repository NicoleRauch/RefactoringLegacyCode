class ValuesOfMonth
  attr_reader :date_of_month, :transactions_of_month, :preceding_balance
  private :date_of_month, :transactions_of_month, :preceding_balance

  def initialize(date_of_month = Date.today, transactions_of_month = [], preceding_balance = 0)
    @date_of_month = date_of_month
    @transactions_of_month = transactions_of_month
    @preceding_balance = preceding_balance
  end

  def balance
    transactions_of_month.map(&:amount).inject(preceding_balance, :+)
  end

  def average_balance
    transactions_of_month.inject(preceding_balance) { |average_balance, transaction|
      average_balance + rate_of(transaction)
    }.to_i
  end

  private
  def rate_of(transaction)
    days_of_month = date_of_month.day
    counting_days = days_of_month - transaction.date.day + 1
    rate = counting_days.to_f / days_of_month.to_f
    transaction.amount * rate
  end
end
