package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.utils.loggerFor

class InMemoryFpsGateway : FpsGateway {

    private companion object {

        private val PORTFOLIOS =
            mapOf(
                1L to Portfolio(
                    customerId = 1,
                    stocks = 6700.toBigDecimal(),
                    bonds = 1200.toBigDecimal(),
                    cash = 400.toBigDecimal()
                ),
                2L to Portfolio(
                    customerId = 2,
                    stocks = 400.toBigDecimal(),
                    bonds = 1200.toBigDecimal(),
                    cash = 6700.toBigDecimal()
                )
            )
    }

    private val log = loggerFor<InMemoryFpsGateway>()

    override fun portfolio(customerId: Long) = PORTFOLIOS[customerId]

    override fun trade(trades: Collection<Trade>) {
        TODO("Not yet implemented")
    }
}
