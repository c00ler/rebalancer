package com.bcgdv.rebalancer.time

import java.time.LocalDate

interface TimeProvider {

    fun today(): LocalDate
}
