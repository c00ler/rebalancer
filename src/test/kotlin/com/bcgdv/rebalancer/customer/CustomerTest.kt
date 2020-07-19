package com.bcgdv.rebalancer.customer

import com.bcgdv.rebalancer.TestData.aCustomer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun `should throw if date is before date of birth`() {
        val date = LocalDate.of(1970, Month.APRIL, 1)
        val dateOfBirth = LocalDate.of(1970, Month.MAY, 1)
        val customer = aCustomer(dateOfBirth = dateOfBirth, retirementAge = 65)

        assertThatThrownBy { customer.yearsToRetirementAt(date) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContainingAll("must be after date of birth", dateOfBirth.toString())
    }
}
