package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.TestData.aPortfolio
import com.bcgdv.rebalancer.TestData.aStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PortfolioTest {

    @Test
    fun `should create a trade to rebalance a portfolio`() {
        val portfolio = aPortfolio(stocks = 5000, bonds = 5000, cash = 5000)
        val strategy = aStrategy(stocksPercentage = 10, cashPercentage = 80, bondsPercentage = 10)

        val trade = portfolio.rebalance(strategy)

        assertThat(trade)
            .isEqualTo(
                Trade(
                    customerId = portfolio.customerId,
                    stocksDelta = (-3500).toBigDecimal(),
                    bondsDelta = (-3500).toBigDecimal(),
                    cashDelta = 7000.toBigDecimal()
                )
            )
    }

    @Test
    fun `should create a trade with decimal values`() {
        val portfolio = aPortfolio(stocks = 5000, bonds = 5000, cash = 5)
        val strategy = aStrategy(stocksPercentage = 35, cashPercentage = 35, bondsPercentage = 30)

        val trade = portfolio.rebalance(strategy)

        assertThat(trade)
            .isEqualTo(
                Trade(
                    customerId = portfolio.customerId,
                    stocksDelta = (-1498.25).toBigDecimal(),
                    bondsDelta = (-1998.5).toBigDecimal(),
                    cashDelta = 3496.75.toBigDecimal()
                )
            )
    }
}
