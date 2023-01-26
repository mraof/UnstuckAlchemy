package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy

class Cost(var value: Elements = Elements()) {
    val ingredients = ArrayList<CostRecipe>()
    var complexity = 0
    var usages = 0
    var recipe = ""

    //Used to prevent infinite recursion in sumIngredients
    private var summing = false

    fun addRecipe(recipe: CostRecipe) {
        this.ingredients.add(recipe)
    }

    fun addRecipe(name: String, ingredients: List<List<Cost>>, produces: Int = 1) {
        addRecipe(CostRecipe(name, ingredients.map { ingredient -> ingredient.map { Pair(it, 1) } }, produces))
    }

    fun addRecipe(name: String, vararg ingredients: Cost) = addRecipe(name, ingredients.map { listOf(it) }.toList())

    // Returns true if ingredients have been summed into the value
    fun sumIngredients(): Boolean {
        usages++
        if (summing) {
            //logger.warn("Recursive cost!")
            return false
        }
        if (value.elements.isNotEmpty()) {
            return true
        }
        if (ingredients.isEmpty()) {
            return false
        }
        summing = true
        val possibleCosts = ingredients
            .mapNotNull { recipe ->
                recipe.cost()
            }
            .filter { it.first.value.elements.isNotEmpty() }
            .sortedBy { it.first.complexity }
        summing = false

        return if (possibleCosts.isNotEmpty()) {
            val cost = possibleCosts[0].first
            recipe = possibleCosts[0].second
            value = cost.value
            complexity = cost.complexity
            ingredients.clear()
            true
        } else {
            false
        }
    }
}
