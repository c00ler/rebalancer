package com.bcgdv.rebalancer.portfolio

import com.bcgdv.rebalancer.TestData.aCustomer
import com.bcgdv.rebalancer.TestData.aPortfolio
import com.bcgdv.rebalancer.customer.CustomersProvider
import com.bcgdv.rebalancer.portfolio.config.FpsProperties
import com.bcgdv.rebalancer.strategy.Strategy
import com.bcgdv.rebalancer.strategy.StrategyAssigner
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PortfolioServiceTest {

    private val customersProviderMock = mockk<CustomersProvider>()
    private val strategyAssignerMock = mockk<StrategyAssigner>()
    private val fpsGatewayMock = mockk<FpsGateway>()

    private val fpsProperties = FpsProperties()

    private val subj = PortfolioService(customersProviderMock, strategyAssignerMock, fpsGatewayMock, fpsProperties)

    @BeforeEach
    fun beforeEach() {
        clearMocks(customersProviderMock, strategyAssignerMock, fpsGatewayMock)
        fpsProperties.batchSize = FpsProperties.DEFAULT_BATCH_SIZE
    }

    @Test
    fun `should do nothing if no customers`() {
        every { customersProviderMock.findAll() } returns emptyList()

        subj.rebalance()

        verify { fpsGatewayMock wasNot Called }
    }

    @Test
    fun `should do nothing if portfolio not found`() {
        val customer = aCustomer()
        every { customersProviderMock.findAll() } returns listOf(customer)
        every { fpsGatewayMock.portfolio(customer.id) } returns null

        subj.rebalance()

        verify { strategyAssignerMock wasNot Called }
    }

    @Test
    fun `should rebalance portfolio according to assigned strategy`() {
        val customer = aCustomer()
        val portfolio = aPortfolio(customerId = customer.id, stocks = 5000, bonds = 5000, cash = 0)
        every { customersProviderMock.findAll() } returns listOf(customer)
        every { fpsGatewayMock.portfolio(customer.id) } returns portfolio
        every { strategyAssignerMock.strategyFor(customer) } returns Strategy.DEFAULT

        val tradesSlot = slot<Collection<Trade>>()
        justRun { fpsGatewayMock.trade(capture(tradesSlot)) }

        subj.rebalance()

        assertThat(tradesSlot.captured)
            .containsExactly(
                Trade(
                    customerId = customer.id,
                    stocksDelta = (-5000).toBigDecimal(),
                    bondsDelta = (-5000).toBigDecimal(),
                    cashDelta = 10000.toBigDecimal()
                )
            )
    }

    @Test
    fun `should execute trades in batches`() {
        val expectedBatchSize = 4
        fpsProperties.batchSize = expectedBatchSize
        val customers =
            (1L..12L).map { customerId -> aCustomer(id = customerId) }
                .onEach { customer ->
                    every { fpsGatewayMock.portfolio(customer.id) } answers {
                        aPortfolio(customerId = customer.id, stocks = 5000, bonds = 5000, cash = 0)
                    }
                }
        every { customersProviderMock.findAll() } returns customers
        every { strategyAssignerMock.strategyFor(any()) } returns Strategy.DEFAULT

        val batches = mutableListOf<Collection<Trade>>()
        justRun { fpsGatewayMock.trade(capture(batches)) }

        subj.rebalance()

        assertThat(batches).hasSize(3)
        assertThat(batches).allMatch { it.size == expectedBatchSize }
        assertThat(batches.flatMap { batch -> batch.map { customer -> customer.customerId } })
            .containsExactlyInAnyOrderElementsOf(customers.map { customer -> customer.id })
    }

    @Test
    fun `should execute trades with smaller batch if there are not enough trades`() {
        fpsProperties.batchSize = 5
        val customers =
            (1L..3L).map { customerId -> aCustomer(id = customerId) }
                .onEach { customer ->
                    every { fpsGatewayMock.portfolio(customer.id) } answers {
                        aPortfolio(customerId = customer.id, stocks = 5000, bonds = 5000, cash = 0)
                    }
                }
        every { customersProviderMock.findAll() } returns customers
        every { strategyAssignerMock.strategyFor(any()) } returns Strategy.DEFAULT

        val tradesSlot = slot<Collection<Trade>>()
        justRun { fpsGatewayMock.trade(capture(tradesSlot)) }

        subj.rebalance()

        assertThat(tradesSlot.captured)
            .containsExactlyInAnyOrderElementsOf(
                customers.map { customer ->
                    Trade(
                        customerId = customer.id,
                        stocksDelta = (-5000).toBigDecimal(),
                        bondsDelta = (-5000).toBigDecimal(),
                        cashDelta = 10000.toBigDecimal()
                    )
                }
            )
    }
}
