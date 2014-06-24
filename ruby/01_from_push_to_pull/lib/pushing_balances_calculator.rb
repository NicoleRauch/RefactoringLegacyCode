require_relative 'values_of_month'

class PushingBalancesCalculator
  attr_reader :transactions
  private :transactions
  def initialize(transactions)
    @transactions = transactions
  end

  def fill_data(balances_of_month_list)
    values_of_month = ValuesOfMonth.new
    balance = 0

    balances_of_month_list.each do |balances_of_month|
      ultimo = balances_of_month.date.day

      average_balance = 0.0
      day_of_latest_balance = 1
      transactions_of_month = transactions_of_month(balances_of_month.date)
      transactions_of_month.each do |transaction|
        day = transaction.date.day
        average_balance += calculate_proportional_balance(day_of_latest_balance, balance, day, ultimo)
        balance += transaction.amount
        day_of_latest_balance = day
      end

      average_balance += calculate_proportional_balance(day_of_latest_balance, balance, ultimo + 1, ultimo)
      values_of_month.set_balance_and_average(balance, average_balance)

      balances_of_month.balance = values_of_month.balance
      balances_of_month.average_balance = values_of_month.average_balance
    end
  end

  private
  def calculate_proportional_balance(day_of_latest_balance, balance, day, days_in_month)
    counting_days = day - day_of_latest_balance
    return 0 if counting_days == 0
    rate = counting_days.to_f / days_in_month.to_f
    balance * rate
  end

  def transactions_of_month(date)
    transactions.select { |transaction|
      are_same_month_and_year(date, transaction.date)
    }
  end

  def are_same_month_and_year(date, date_of_transaction)
    date_of_transaction.month == date.month &&
      date_of_transaction.year == date.year
  end
end
