require_relative '../lib/pushing_balances_calculator'
require_relative '../../spec/balances_calculator_examples'

describe PushingBalancesCalculator do
  let(:calculator) { PushingBalancesCalculator.new(transactions) }
  it_behaves_like 'a balances calculator'
end
