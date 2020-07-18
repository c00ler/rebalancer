package com.bcgdv.rebalancer.customer

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import java.time.LocalDate
import java.time.Month

class CsvCustomersProviderTest {

    private companion object {
        private val TEST_CUSTOMERS = IOUtils.toByteArray(ClassPathResource("test_customers.csv").inputStream)
    }

    private val subj = CsvCustomersProvider(ByteArrayResource(TEST_CUSTOMERS))

    @Test
    fun `should return all customers`() {
        val customers = subj.findAll()

        assertThat(customers)
            .containsExactlyInAnyOrder(
                Customer(
                    id = 1,
                    email = "bob@bob.com",
                    dateOfBirth = LocalDate.of(1961, Month.APRIL, 29),
                    riskLevel = 3,
                    retirementAge = 65
                ),
                Customer(
                    id = 2,
                    email = "sally@gmail.com",
                    dateOfBirth = LocalDate.of(1978, Month.MAY, 1),
                    riskLevel = 8,
                    retirementAge = 67
                )
            )
    }
}
