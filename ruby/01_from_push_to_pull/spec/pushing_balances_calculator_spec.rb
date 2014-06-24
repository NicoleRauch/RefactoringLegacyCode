require 'date'

require_relative '../lib/pushing_balances_calculator'
require_relative '../../lib/transaction'
require_relative '../../lib/balances_of_month'

describe PushingBalancesCalculator do
  let(:april_ultimo) { Date.new(2010, 4, 30) }
  let(:may_ultimo) { Date.new(2010, 5, 31) }
  let(:june_ultimo) { Date.new(2010, 6, 30) }

  let(:calculator) { PushingBalancesCalculator.new(transactions) }

  before { calculator.fill_data(balances) }

  let(:april_data) { balances.at(0) }
  context 'with one transaction at first of month' do
    let(:transactions) {
      [
        Transaction.new(Date.new(2010, 4, 1), 100)
      ]
    }

    let(:balances) {
      [
        BalancesOfMonth.new(april_ultimo)
      ]
    }

    it 'sets the balance and average' do
      expect(april_data.balance).to eq 100
      expect(april_data.average_balance).to eq 100
    end
  end

  context 'with a transaction a the ultimo of month' do
    let(:transactions) {
      [
        Transaction.new(Date.new(2010, 4, 30), 300)
      ]
    }

    let(:balances) {
      [
        BalancesOfMonth.new(april_ultimo)
      ]
    }

    it 'sets the balance and average' do
      expect(april_data.balance).to eq 300
      expect(april_data.average_balance).to eq 10
    end
  end

  context 'two transactions at fist and 16th of month' do
    let(:transactions) {
      [
        Transaction.new(Date.new(2010, 4, 1),  100),
        Transaction.new(Date.new(2010, 4, 30), 200)
      ]
    }

    let(:balances) {
      [
        BalancesOfMonth.new(april_ultimo)
      ]
    }

    it 'sets the average balance to the mid of start and the ultimo balance' do
      expect(april_data.balance).to eq 300
      expect(april_data.average_balance).to eq 200
    end
  end
end
