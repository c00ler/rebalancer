package com.bcgdv.rebalancer.strategy

import com.bcgdv.rebalancer.TestData.aCustomer
import com.bcgdv.rebalancer.TestData.aStrategy
import com.bcgdv.rebalancer.time.TimeProvider
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class StrategyAssignerTest {

    private val customer =
        aCustomer(
            dateOfBirth = LocalDate.of(1980, Month.JANUARY, 1),
            riskLevel = 4,
            retirementAge = 40
        )

    private val strategiesProviderMock = mockk<StrategiesProvider>()
    private val timeProviderMock = mockk<TimeProvider>()

    private val subj = StrategyAssigner(strategiesProviderMock, timeProviderMock)

    @BeforeEach
    fun beforeEach() {
        clearMocks(strategiesProviderMock, timeProviderMock)
    }

    @Test
    fun `should return default strategy if no strategies`() {
        every { strategiesProviderMock.findAll() } returns emptyList()
        every { timeProviderMock.today() } returns LocalDate.now()

        val strategy = subj.strategyFor(customer)

        assertThat(strategy).isSameAs(Strategy.DEFAULT)
    }

    @Test
    fun `should return default strategy if risk level doesn't match any strategy`() {
        every { strategiesProviderMock.findAll() } returns
                listOf(
                    aStrategy(
                        riskLevelRange = 0..3,
                        yearsToRetirementRange = 20..30
                    )
                )
        every { timeProviderMock.today() } returns LocalDate.of(1995, Month.JANUARY, 1)

        val strategy = subj.strategyFor(customer)

        assertThat(strategy).isSameAs(Strategy.DEFAULT)
    }

    @Test
    fun `should default strategy if years to retirement doesn't match any strategy`() {
        every { strategiesProviderMock.findAll() } returns
                listOf(
                    aStrategy(
                        riskLevelRange = 0..5,
                        yearsToRetirementRange = 30..40
                    )
                )
        every { timeProviderMock.today() } returns LocalDate.of(1995, Month.JANUARY, 1)

        val strategy = subj.strategyFor(customer)

        assertThat(strategy).isSameAs(Strategy.DEFAULT)
    }

    @Test
    fun `should assign matching strategy to the customer`() {
        val expectedStrategy = aStrategy(riskLevelRange = 0..5, yearsToRetirementRange = 20..30)
        every { strategiesProviderMock.findAll() } returns listOf(expectedStrategy)
        every { timeProviderMock.today() } returns LocalDate.of(1995, Month.JANUARY, 1)

        val strategy = subj.strategyFor(customer)

        assertThat(strategy).isSameAs(expectedStrategy)
    }
}
