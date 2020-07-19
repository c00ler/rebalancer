package com.bcgdv.rebalancer.strategy

// For simplicity only integer percentages are allowed
data class Strategy(
    val id: Long,
    val riskLevelRange: IntRange = IntRange.EMPTY,
    val yearsToRetirementRange: IntRange = IntRange.EMPTY,
    val stocksPercentage: Int, // TODO: probably it's better to extract allocation into a separate object
    val cashPercentage: Int,
    val bondsPercentage: Int
) {

    companion object {

        private const val REQUIRED_PERCENTAGE = 100
        private const val DEFAULT_STRATEGY_ID = -1L

        val DEFAULT =
            Strategy(
                id = DEFAULT_STRATEGY_ID,
                stocksPercentage = 0,
                cashPercentage = 100,
                bondsPercentage = 0
            )
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

    fun appliesTo(riskLevel: Int, yearsToRetirement: Int) =
        riskLevelRange.contains(riskLevel) && yearsToRetirementRange.contains(yearsToRetirement)
}
