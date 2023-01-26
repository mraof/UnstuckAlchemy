package com.mraof.unstuckalchemy.alchemy

data class Elements(val elements: Map<String, Double>) {
    constructor(element: String, amount: Double) : this(mapOf(element to amount))
    constructor(element: String, amount: Long) : this(mapOf(element to amount.toDouble()))
    constructor() : this(emptyMap())

    operator fun plus(other: Elements): Elements {
        val newMap = HashMap(this.elements)
        for ((element, amount) in other.elements) {
            newMap[element] = newMap.getOrDefault(element, 0.0) + amount
        }
        return Elements(newMap)
    }

    operator fun div(other: Int): Elements {
        return Elements(this.elements.mapValues { (_, amount) -> amount / other })
    }

    operator fun times(other: Int): Elements {
        return Elements(this.elements.mapValues { (_, amount) -> amount * other })
    }

    fun isEmpty() = elements.isEmpty()

    override fun toString(): String {
        return elements.toString()
    }

}
