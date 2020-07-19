package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.strategy.Strategy
import com.bcgdv.rebalancer.utils.extensions.percentOf
import java.math.BigDecimal

data class Portfolio(
    val customerId: Long,
    val stocks: BigDecimal,
    val bonds: BigDecimal,
    val cash: BigDecimal
) {

    /**
     * Rebalances portfolio according to provided strategy.
     *
     * @return [Trade] object that contains changes to the portfolio
     */
    fun rebalance(strategy: Strategy): Trade {
        val total = stocks + bonds + cash

        with(strategy) {
            return Trade(
                customerId = customerId,
                stocksDelta = stocksPercentage.percentOf(total) - stocks,
                bondsDelta = bondsPercentage.percentOf(total) - bonds,
                cashDelta = cashPercentage.percentOf(total) - cash
            )
        }
    }
}

data class Trade(
    val customerId: Long,
    val stocksDelta: BigDecimal,
    val bondsDelta: BigDecimal,
    val cashDelta: BigDecimal
)
