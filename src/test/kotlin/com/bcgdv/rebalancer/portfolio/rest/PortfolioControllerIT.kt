package com.bcgdv.rebalancer.portfolio.rest

import com.bcgdv.rebalancer.AbstractIT
import com.bcgdv.rebalancer.test.TestFpsGateway
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

class PortfolioControllerIT : AbstractIT() {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var testFpsGateway: TestFpsGateway

    @BeforeEach
    fun beforeEach() {
        testFpsGateway.batches.clear()
    }

    @Test
    fun `should trigger all portfolios rebalancing`() {
        val entity = restTemplate.postForEntity<String>("/portfolios/rebalance")

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).isNull()

        assertThat(testFpsGateway.batches).hasSize(3)
        assertThat(testFpsGateway.batches.flatMap { batch -> batch.map { trade -> trade.customerId } })
            .containsExactlyInAnyOrder(1, 2, 3, 4, 5)
    }
}
