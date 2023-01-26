package com.mraof.unstuckalchemy.alchemy

// Class to simplify working with the cost map
class CostMap() {
    private val costs = LinkedHashMap<SimpleStack, Cost>()

    operator fun get(stack: SimpleStack): Cost = costs.getOrPut(stack) { Cost() }

    fun getCost(stack: SimpleStack): Cost? = costs[stack]

    operator fun iterator() = costs.iterator()
    fun putAll(map: Map<SimpleStack, Cost>) {
        costs.putAll(map)
    }
}
