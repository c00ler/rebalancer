package com.bcgdv.rebalancer.customer.config

import com.bcgdv.rebalancer.customer.CsvCustomersProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class CustomerConfig {

    @Bean
    fun customersProvider() = CsvCustomersProvider(ClassPathResource("customers.csv"))
}
