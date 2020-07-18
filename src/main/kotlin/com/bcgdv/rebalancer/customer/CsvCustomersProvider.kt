package com.bcgdv.rebalancer.customer

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.Resource
import java.nio.charset.StandardCharsets
import java.time.LocalDate

class CsvCustomersProvider(
    private val customersResource: Resource,
    private val format: CSVFormat = CSVFormat.DEFAULT.withHeader()
) : CustomersProvider {

    override fun findAll(): Collection<Customer> {
        return CSVParser.parse(customersResource.inputStream, StandardCharsets.UTF_8, format)
            .use { parser ->
                parser.records
                    .map { record ->
                        Customer(
                            id = record[CUSTOMER_ID].toLong(),
                            email = record[EMAIL],
                            dateOfBirth = record[DATE_OF_BIRTH].toDateOfBirth(),
                            riskLevel = record[RISK_LEVEL].toInt(),
                            retirementAge = record[RETIREMENT_AGE].toInt()
                        )
                    }
            }
    }

    private companion object {

        private const val CUSTOMER_ID = "customerId"
        private const val EMAIL = "email"
        private const val DATE_OF_BIRTH = "dateOfBirth"
        private const val RISK_LEVEL = "riskLevel"
        private const val RETIREMENT_AGE = "retirementAge"
    }
}

private fun String.toDateOfBirth() = LocalDate.parse(this)
