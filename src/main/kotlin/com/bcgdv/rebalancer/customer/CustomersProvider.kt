package com.bcgdv.rebalancer.customer

interface CustomersProvider {

    fun findAll(): Collection<Customer>
}
