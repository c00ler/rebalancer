package com.bcgdv.rebalancer.portfolio.config

import com.bcgdv.rebalancer.portfolio.InMemoryFpsGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PortfolioConfig {

    @Bean
    fun fpsGateway() = InMemoryFpsGateway()
}
