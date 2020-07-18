package com.bcgdv.rebalancer.strategy

import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource

class CsvStrategiesProviderTest {

    private companion object {
        private val TEST_STRATEGIES = IOUtils.toByteArray(ClassPathResource("test_strategy.csv").inputStream)
    }

    private val subj = CsvStrategiesProvider(ByteArrayResource(TEST_STRATEGIES))

    @Test
    fun `should return all strategies`() {
        val strategies = subj.findAll()

        assertThat(strategies)
            .containsExactlyInAnyOrder(
                Strategy(
                    id = 1,
                    riskLevelRange = IntRange(0, 3),
                    yearsToRetirementRange = IntRange(20, 30),
                    stocksPercentage = 20,
                    cashPercentage = 20,
                    bondsPercentage = 60
                ),
                Strategy(
                    id = 2,
                    riskLevelRange = IntRange(0, 3),
                    yearsToRetirementRange = IntRange(10, 20),
                    stocksPercentage = 10,
                    cashPercentage = 20,
                    bondsPercentage = 70
                ),
                Strategy(
                    id = 3,
                    riskLevelRange = IntRange(6, 9),
                    yearsToRetirementRange = IntRange(20, 30),
                    stocksPercentage = 10,
                    cashPercentage = 0,
                    bondsPercentage = 90
                )
            )
    }
}
