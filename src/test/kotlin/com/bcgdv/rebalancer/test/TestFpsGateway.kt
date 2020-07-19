package com.bcgdv.rebalancer.test

import com.bcgdv.rebalancer.portfolio.InMemoryFpsGateway
import com.bcgdv.rebalancer.portfolio.Trade
import com.bcgdv.rebalancer.utils.loggerFor
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArrayList

@Component
class TestFpsGateway : InMemoryFpsGateway() {

    private val log = loggerFor<InMemoryFpsGateway>()

    val batches = CopyOnWriteArrayList<Collection<Trade>>()

    override fun trade(trades: Collection<Trade>) {
        log.info("Received new batch of size {}", trades.size)
        batches.add(trades)
    }
}
