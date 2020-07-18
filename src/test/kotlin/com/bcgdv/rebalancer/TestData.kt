package com.bcgdv.rebalancer

import com.bcgdv.rebalancer.customer.Customer
import com.bcgdv.rebalancer.portfolio.Portfolio
import com.bcgdv.rebalancer.strategy.Strategy
import java.time.LocalDate
import java.time.Month

object TestData {

    fun aCustomer(
        id: Long = 1,
        email: String = "bob@bob.com",
        dateOfBirth: LocalDate = LocalDate.of(1961, Month.APRIL, 29),
        riskLevel: Int = 3,
        retirementAge: Int = 65
    ) =
        Customer(
            id = id,
            email = email,
            dateOfBirth = dateOfBirth,
            riskLevel = riskLevel,
            retirementAge = retirementAge
        )

    fun aStrategy(
        id: Long = 1,
        riskLevelRange: IntRange = 0..3,
        yearsToRetirementRange: IntRange = 20..30,
        stocksPercentage: Int = 20,
        cashPercentage: Int = 20,
        bondsPercentage: Int = 60
    ) =
        Strategy(
            id = id,
            riskLevelRange = riskLevelRange,
            yearsToRetirementRange = yearsToRetirementRange,
            stocksPercentage = stocksPercentage,
            cashPercentage = cashPercentage,
            bondsPercentage = bondsPercentage
        )

    fun aPortfolio(
        customerId: Long = 1,
        stocks: Long = 6700,
        bonds: Long = 1200,
        cash: Long = 400
    ) =
        Portfolio(
            customerId = customerId,
            stocks = stocks.toBigDecimal(),
            bonds = bonds.toBigDecimal(),
            cash = cash.toBigDecimal()
        )
}
