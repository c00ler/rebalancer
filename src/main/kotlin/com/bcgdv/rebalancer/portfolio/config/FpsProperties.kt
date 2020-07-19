package com.bcgdv.rebalancer.portfolio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Positive

@ConfigurationProperties(prefix = "fps")
@Validated
class FpsProperties {

    @Positive
    var batchSize: Int = 1
}
