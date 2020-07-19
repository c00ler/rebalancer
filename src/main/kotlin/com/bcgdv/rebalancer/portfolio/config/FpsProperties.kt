package com.bcgdv.rebalancer.portfolio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Positive

@ConfigurationProperties(prefix = "fps")
@Validated
class FpsProperties {

    companion object {

        const val DEFAULT_BATCH_SIZE = 1
    }

    @Positive
    var batchSize: Int = DEFAULT_BATCH_SIZE
}
