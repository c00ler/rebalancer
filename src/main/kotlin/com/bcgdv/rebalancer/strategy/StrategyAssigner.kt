package com.bcgdv.rebalancer.strategy

import com.bcgdv.rebalancer.customer.Customer
import com.bcgdv.rebalancer.time.TimeProvider
import org.springframework.stereotype.Service

@Service
class StrategyAssigner(
    private val strategiesProvider: StrategiesProvider,
    private val timeProvider: TimeProvider
) {

    /**
     * Will retrieve all strategies on every call. Optimization is outside of the scope of the task.
     */
    fun strategyFor(customer: Customer): Strategy {
        val yearsToRetirement = customer.yearsToRetirementAt(timeProvider.today())
        return strategiesProvider.findAll()
            .find { strategy -> strategy.appliesTo(customer.riskLevel, yearsToRetirement) } ?: Strategy.DEFAULT
    }
}
