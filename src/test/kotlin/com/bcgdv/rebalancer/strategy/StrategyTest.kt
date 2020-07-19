package com.bcgdv.rebalancer.strategy

import com.bcgdv.rebalancer.TestData.aStrategy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class StrategyTest {

    @Test
    fun `should validate total percentage`() {
        assertThatThrownBy { aStrategy(stocksPercentage = 10, cashPercentage = 10, bondsPercentage = 10) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContainingAll("must be equal to 100%", "but is 30%")
    }

    @TestFactory
    fun `should validate that all percentages are positive`() =
        listOf(
            dynamicTest("stocks percentage is negative") {
                assertThatThrownBy { aStrategy(stocksPercentage = -10, cashPercentage = 60, bondsPercentage = 50) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContainingAll("All percentages must be positive")
            },
            dynamicTest("cash percentage is negative") {
                assertThatThrownBy { aStrategy(stocksPercentage = 60, cashPercentage = -10, bondsPercentage = 50) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContainingAll("All percentages must be positive")
            },
            dynamicTest("bonds percentage is negative") {
                assertThatThrownBy { aStrategy(stocksPercentage = 60, cashPercentage = 50, bondsPercentage = -10) }
                    .isInstanceOf(IllegalArgumentException::class.java)
                    .hasMessageContainingAll("All percentages must be positive")
            }
        )

    @TestFactory
    fun `should include range boundaries when checking if strategy applies`(): Collection<DynamicTest> {
        val strategy = aStrategy(riskLevelRange = 0..3, yearsToRetirementRange = 20..30)
        return listOf(
            dynamicTest("minimum risk level") {
                assertThat(strategy.appliesTo(0, 25)).isTrue()
            },
            dynamicTest("maximum risk level") {
                assertThat(strategy.appliesTo(3, 25)).isTrue()
            },
            dynamicTest("minimum years to retirement") {
                assertThat(strategy.appliesTo(1, 20)).isTrue()
            },
            dynamicTest("maximum years to retirement") {
                assertThat(strategy.appliesTo(1, 30)).isTrue()
            }
        )
    }
}
