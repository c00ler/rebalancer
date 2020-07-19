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

    /**
     * Calculates years to retirement at a given date.
     */
    fun yearsToRetirementAt(date: LocalDate): Int {
        require(date.isAfter(dateOfBirth)) {
            "Date must be after date of birth $dateOfBirth"
        }

        return retirementAge - Period.between(dateOfBirth, date).years
    }
}
