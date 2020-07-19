package com.bcgdv.rebalancer.portfolio.config

import com.bcgdv.rebalancer.portfolio.InMemoryFpsGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Do not include this configuration if **test** profile is activated. Different implementation will be used in tests.
 */
@Profile("!test")
@Configuration
class PortfolioConfig {

    @Bean
    fun fpsGateway() = InMemoryFpsGateway()
}
