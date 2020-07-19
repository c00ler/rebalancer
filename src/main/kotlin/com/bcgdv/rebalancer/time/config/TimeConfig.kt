package com.bcgdv.rebalancer.time.config

import com.bcgdv.rebalancer.time.UtcTimeProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TimeConfig {

    @Bean
    fun timeProvider() = UtcTimeProvider()
}
