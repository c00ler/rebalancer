package com.bcgdv.rebalancer.utils.extensions

import java.math.BigDecimal

fun Int.percentOf(total: BigDecimal): BigDecimal =
    total.multiply(this.toBigDecimal()).divide(100.toBigDecimal())
