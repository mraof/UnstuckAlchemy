package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy

/**
 * Created by mraof on 2019 November 20 at 6:13 AM.
 */
class CostRecipe(val name: String, val ingredients: List<List<Pair<Cost, Int>>>, val divisor: Int = 1, val multiplier: Int = 1) {
    fun cost(): Pair<Cost, String>? {
        var elements = Elements()
        var complexity = 0
        for (ingredientList in ingredients) {
            val filteredList = ingredientList
                .filter { it.first.sumIngredients() }
                .sortedBy { it.first.complexity }
            if (filteredList.isEmpty()) {
                return null
            }
            if(name == "techreborn:centrifuge/gold_dust") {
                UnstuckAlchemy.logger.debug("meow")
            }
            val ingredient = filteredList[0].first
            val amount = filteredList[0].second
            elements += ingredient.value * amount
            complexity += ingredient.complexity
        }
        elements *= multiplier
        elements /= divisor
        val cost = Cost(elements)
        cost.complexity = complexity + 1
        return Pair(cost, name)
    }

    override fun toString(): String {
        return "CostRecipe(name='$name', ingredients=$ingredients, divisor=$divisor, multiplier=$multiplier)"
    }
}
