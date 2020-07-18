package com.bcgdv.rebalancer.strategy.config

import com.bcgdv.rebalancer.strategy.CsvStrategiesProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class StrategyConfig {

    @Bean
    fun strategyProvider() = CsvStrategiesProvider(ClassPathResource("strategy.csv"))
}
