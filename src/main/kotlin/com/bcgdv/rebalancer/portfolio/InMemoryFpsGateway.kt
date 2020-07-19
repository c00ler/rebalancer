package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.utils.loggerFor

class InMemoryFpsGateway : FpsGateway {

    private companion object {

        private val PORTFOLIOS =
            listOf(
                Portfolio(
                    customerId = 1, stocks = 6700.toBigDecimal(), bonds = 1200.toBigDecimal(), cash = 400.toBigDecimal()
                ),
                Portfolio(
                    customerId = 2, stocks = 400.toBigDecimal(), bonds = 1200.toBigDecimal(), cash = 6700.toBigDecimal()
                ),
                Portfolio(
                    customerId = 3, stocks = 1000.toBigDecimal(), bonds = 5000.toBigDecimal(), cash = 700.toBigDecimal()
                ),
                Portfolio(
                    customerId = 4, stocks = 5.toBigDecimal(), bonds = 1000.toBigDecimal(), cash = 3456.toBigDecimal()
                ),
                Portfolio(
                    customerId = 5, stocks = 464.toBigDecimal(), bonds = 8000.toBigDecimal(), cash = 1214.toBigDecimal()
                )
            ).associateBy { it.customerId }
    }

    private val log = loggerFor<InMemoryFpsGateway>()

    override fun portfolio(customerId: Long) = PORTFOLIOS[customerId]

    override fun trade(trades: Collection<Trade>) {
        log.info(">> Received batch of {} trades", trades.size)
        trades.forEach { log.info(">> {}", it) }
        log.info(">> ---")
    }
}
