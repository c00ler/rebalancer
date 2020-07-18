package com.bcgdv.rebalancer.customer

import java.time.LocalDate
import java.time.Period

// TODO: Think about validation
data class Customer(
    val id: Long,
    val email: String,
    val dateOfBirth: LocalDate,
    val riskLevel: Int,
    val retirementAge: Int
) {
    fun yearsToRetirementAt(date: LocalDate) = retirementAge - Period.between(dateOfBirth, date).years
}
