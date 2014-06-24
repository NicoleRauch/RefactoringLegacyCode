require 'date'

require_relative '../lib/transaction'
require_relative '../lib/balances_of_month'

shared_examples 'a balances calculator' do
  let(:april_ultimo) { Date.new(2010, 4, 30).freeze }
  let(:may_ultimo) { Date.new(2010, 5, 31).freeze }
  let(:june_ultimo) { Date.new(2010, 6, 30).freeze }

  before { calculator.fill_data(balances) }

  let(:april_data) { balances.at(0) }
  let(:may_data) { balances.at(1) }
  let(:june_data) { balances.at(2) }

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
        Transaction.new(Date.new(2010, 4, 16), 200)
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

  context 'transactions spanning over two months' do
    let(:transactions) {
      [
        Transaction.new(Date.new(2010, 4, 16), 100),
        Transaction.new(Date.new(2010, 5, 16), 200)
      ]
    }

    let(:balances) {
      [
        BalancesOfMonth.new(april_ultimo),
        BalancesOfMonth.new(may_ultimo)
      ]
    }

    it 'factors in the balance of the previous month' do
      expect(april_data.balance).to eq 100
      expect(april_data.average_balance).to eq 50

      expect(may_data.balance).to eq 300
      expect(may_data.average_balance).to eq 203
    end
  end

  context 'transactions spanning over two months' do
    let(:transactions) {
      [
        Transaction.new(Date.new(2010, 4, 16), 100),
        Transaction.new(Date.new(2010, 5, 16), 200)
      ]
    }

    let(:balances) {
      [
        BalancesOfMonth.new(april_ultimo),
        BalancesOfMonth.new(may_ultimo),
        BalancesOfMonth.new(june_ultimo)
      ]
    }

    it 'the accumulation of the balance is carried forward' do
      expect(april_data.balance).to eq 100
      expect(april_data.average_balance).to eq 50

      expect(may_data.balance).to eq 300
      expect(may_data.average_balance).to eq 203
    end
  end
end


