require_relative 'values_of_month'

class PushingBalancesCalculator
  attr_reader :transactions
  private :transactions
  def initialize(transactions)
    @transactions = transactions
  end

  def fill_data(balances_of_month_list)
    values_of_month = ValuesOfMonth.new

    balances_of_month_list.each do |balances_of_month|
      date_of_month = balances_of_month.date
      transactions_of_month = transactions_of_month(date_of_month)

      preceding_balance = values_of_month.balance

      values_of_month = ValuesOfMonth.new(date_of_month, transactions_of_month, preceding_balance)

      balances_of_month.balance = values_of_month.balance
      balances_of_month.average_balance = values_of_month.average_balance
    end
  end

  private
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
