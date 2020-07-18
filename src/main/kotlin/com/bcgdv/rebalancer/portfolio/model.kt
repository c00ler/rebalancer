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

    fun rebalance(strategy: Strategy): Trade {
        val total = stocks + bonds + cash
        return Trade(
            customerId = customerId,
            stocksDelta = strategy.stocksPercentage.percentOf(total) - stocks,
            bondsDelta = strategy.bondsPercentage.percentOf(total) - bonds,
            cashDelta = strategy.cashPercentage.percentOf(total) - cash
        )
    }
}

data class Trade(
    val customerId: Long,
    val stocksDelta: BigDecimal,
    val bondsDelta: BigDecimal,
    val cashDelta: BigDecimal
)
