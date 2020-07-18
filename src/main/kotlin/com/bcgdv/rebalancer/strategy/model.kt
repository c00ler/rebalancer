package com.bcgdv.rebalancer.strategy

// For simplicity only integer percentages are allowed
data class Strategy(
    val id: Long,
    val riskLevelRange: IntRange,
    val yearsToRetirementRange: IntRange,
    val stocksPercentage: Int,
    val cashPercentage: Int,
    val bondsPercentage: Int
) {

    private companion object {

        private const val REQUIRED_PERCENTAGE = 100
    }

    init {
        require(stocksPercentage >= 0 && cashPercentage >= 0 && bondsPercentage >= 0) {
            "All percentages must be positive"
        }

        val totalPercentage = stocksPercentage + cashPercentage + bondsPercentage
        require(totalPercentage == REQUIRED_PERCENTAGE) {
            "Total percentage must be equal to $REQUIRED_PERCENTAGE%, but is $totalPercentage%"
        }
    }
}
