package com.bcgdv.rebalancer.customer

import com.bcgdv.rebalancer.ApplicationDefaults
import com.bcgdv.rebalancer.utils.CsvSource
import com.bcgdv.rebalancer.utils.extensions.toLocalDate
import org.apache.commons.csv.CSVFormat
import org.springframework.core.io.Resource

class CsvCustomersProvider(
    private val customersCsv: Resource,
    private val format: CSVFormat = ApplicationDefaults.csvFormat
) : CustomersProvider, CsvSource {

    private companion object {

        private const val CUSTOMER_ID = "customerId"
        private const val EMAIL = "email"
        private const val DATE_OF_BIRTH = "dateOfBirth"
        private const val RISK_LEVEL = "riskLevel"
        private const val RETIREMENT_AGE = "retirementAge"
    }

    override fun findAll(): Collection<Customer> {
        return parseCsv(customersCsv, format) { record ->
            Customer(
                id = record[CUSTOMER_ID].toLong(),
                email = record[EMAIL],
                dateOfBirth = record[DATE_OF_BIRTH].toLocalDate(),
                riskLevel = record[RISK_LEVEL].toInt(),
                retirementAge = record[RETIREMENT_AGE].toInt()
            )
        }
    }
}
