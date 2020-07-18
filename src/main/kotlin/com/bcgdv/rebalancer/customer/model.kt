package com.bcgdv.rebalancer.customer

import java.time.LocalDate
import java.time.Period
import java.time.ZoneOffset

// TODO: Think about validation
data class Customer(
    val id: Long,
    val email: String,
    val dateOfBirth: LocalDate,
    val riskLevel: Int,
    val retirementAge: Int
) {
    val age: Int
        get() = Period.between(dateOfBirth, LocalDate.now(ZoneOffset.UTC)).years
}
