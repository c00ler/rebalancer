package com.bcgdv.rebalancer.strategy

data class Strategy(
    val id: Long,
    val riskLevelRange: IntRange,
    val yearsToRetirementRange: IntRange,
    val stocksPercentage: Int,
    val cashPercentage: Int,
    val bondsPercentage: Int
)
