package com.bcgdv.rebalancer

import com.bcgdv.rebalancer.customer.Customer
import java.time.LocalDate
import java.time.Month

object CustomerTestData {

    fun aCustomer() =
        Customer(
            id = 1,
            email = "bob@bob.com",
            dateOfBirth = LocalDate.of(1961, Month.APRIL, 29),
            riskLevel = 3,
            retirementAge = 65
        )
}
