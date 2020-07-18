package com.bcgdv.rebalancer.utils.extensions

import java.time.LocalDate

fun String.toLocalDate(): LocalDate = LocalDate.parse(this)
