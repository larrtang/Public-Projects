from market import Market

YHOO = Market("YHOO")

print YHOO.price

YHOO.pull_prices()

