package com.bcgdv.rebalancer.customer

import com.bcgdv.rebalancer.TestData.aCustomer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class CustomerTest {

    @Test
    fun `should calculate years to retirement`() {
        val date = LocalDate.of(2020, Month.MAY, 1)
        val customer = aCustomer(dateOfBirth = LocalDate.of(1970, Month.MAY, 1), retirementAge = 65)

        assertThat(customer.yearsToRetirementAt(date)).isEqualTo(15)
    }
}
