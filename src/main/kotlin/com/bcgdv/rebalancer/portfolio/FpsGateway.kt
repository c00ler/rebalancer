package com.bcgdv.rebalancer.portfolio

interface FpsGateway {

    fun portfolio(customerId: Long): Portfolio?

    fun trade(trades: Collection<Trade>)
}
