package com.bcgdv.rebalancer.strategy

import com.bcgdv.rebalancer.ApplicationDefaults
import com.bcgdv.rebalancer.utils.CsvSource
import org.apache.commons.csv.CSVFormat
import org.springframework.core.io.Resource

class CsvStrategiesProvider(
    private val strategiesCsv: Resource,
    private val format: CSVFormat = ApplicationDefaults.csvFormat
) : StrategiesProvider, CsvSource {

    private companion object {

        private const val STRATEGY_ID = "strategyId"
        private const val MIN_RISK_LEVEL = "minRiskLevel"
        private const val MAX_RISK_LEVEL = "maxRiskLevel"
        private const val MIN_YEARS_TO_RETIREMENT = "minYearsToRetirement"
        private const val MAX_YEARS_TO_RETIREMENT = "maxYearsToRetirement"
        private const val STOCKS_PERCENTAGE = "stocksPercentage"
        private const val CASH_PERCENTAGE = "cashPercentage"
        private const val BONDS_PERCENTAGE = "bondsPercentage"
    }

    override fun findAll(): Collection<Strategy> {
        return parseCsv(strategiesCsv, format) { record ->
            Strategy(
                id = record[STRATEGY_ID].toLong(),
                riskLevelRange = IntRange(
                    record[MIN_RISK_LEVEL].toInt(), record[MAX_RISK_LEVEL].toInt()
                ),
                yearsToRetirementRange = IntRange(
                    record[MIN_YEARS_TO_RETIREMENT].toInt(), record[MAX_YEARS_TO_RETIREMENT].toInt()
                ),
                stocksPercentage = record[STOCKS_PERCENTAGE].toInt(),
                cashPercentage = record[CASH_PERCENTAGE].toInt(),
                bondsPercentage = record[BONDS_PERCENTAGE].toInt()
            )
        }
    }
}
