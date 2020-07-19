package com.bcgdv.rebalancer.strategy

import com.bcgdv.rebalancer.customer.Customer
import com.bcgdv.rebalancer.time.TimeProvider
import com.bcgdv.rebalancer.utils.loggerFor
import org.springframework.stereotype.Service

@Service
class StrategyAssigner(
    private val strategiesProvider: StrategiesProvider,
    private val timeProvider: TimeProvider
) {

    private val log = loggerFor<StrategyAssigner>()

    /**
     * Will retrieve all strategies on every call. Optimization is outside of the scope of the task.
     */
    fun strategyFor(customer: Customer): Strategy {
        val yearsToRetirement = customer.yearsToRetirementAt(timeProvider.today())
        log.debug("customer={}, yearsToRetirement={}", customer.id, yearsToRetirement)

        val assignedStrategy = strategiesProvider.findAll()
            .find { strategy -> strategy.appliesTo(customer.riskLevel, yearsToRetirement) } ?: Strategy.DEFAULT
        log.info("Assigned strategy={} to customer={} - {}", assignedStrategy.id, customer.id, assignedStrategy)

        return assignedStrategy
    }
}
