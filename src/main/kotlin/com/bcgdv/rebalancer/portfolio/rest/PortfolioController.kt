package com.bcgdv.rebalancer.portfolio.rest

import com.bcgdv.rebalancer.portfolio.PortfolioService
import com.bcgdv.rebalancer.utils.loggerFor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/portfolios")
class PortfolioController(
    private val portfolioService: PortfolioService
) {

    private val log = loggerFor<PortfolioController>()

    @PostMapping("/rebalance")
    fun rebalance() {
        log.info("Starting rebalancing of all portfolios")
        portfolioService.rebalance()
        log.info("Rebalancing finished")
    }
}
