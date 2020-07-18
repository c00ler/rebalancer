package com.bcgdv.rebalancer.strategy

interface StrategiesProvider {

    fun findAll(): Collection<Strategy>
}
