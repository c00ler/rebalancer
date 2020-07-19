package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.customer.Customer
import com.bcgdv.rebalancer.customer.CustomersProvider
import com.bcgdv.rebalancer.portfolio.config.FpsProperties
import com.bcgdv.rebalancer.strategy.StrategyAssigner
import com.bcgdv.rebalancer.utils.loggerFor
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val customersProvider: CustomersProvider,
    private val strategyAssigner: StrategyAssigner,
    private val fpsGateway: FpsGateway,
    private val fpsProperties: FpsProperties
) {

    private val log = loggerFor<PortfolioService>()

    /**
     * Main logic of the service.
     *
     * For each customer it requests their portfolio, rebalances it according to the selected strategy and executes
     * trades in batches.
     */
    fun rebalance() =
        customersProvider.findAll()
            .asSequence()
            .mapNotNull(::rebalanceCustomerPortfolio)
            .chunked(fpsProperties.batchSize)
            .forEach(::executeTrades)

    private fun rebalanceCustomerPortfolio(customer: Customer): Trade? {
        val portfolio = fpsGateway.portfolio(customer.id) ?: run {
            log.warn("Portfolio for customer={} not found", customer.id)
            return null
        }
        val strategy = strategyAssigner.strategyFor(customer)
        return portfolio.rebalance(strategy)
    }

    private fun executeTrades(trades: Collection<Trade>) {
        fpsGateway.trade(trades)
        log.info("Successfully executed batch of {} trades", trades.size)
    }
}
