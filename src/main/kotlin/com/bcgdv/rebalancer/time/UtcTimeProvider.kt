package com.bcgdv.rebalancer.time

import java.time.LocalDate
import java.time.ZoneOffset

class UtcTimeProvider : TimeProvider {

    override fun today(): LocalDate = LocalDate.now(ZoneOffset.UTC)
}
