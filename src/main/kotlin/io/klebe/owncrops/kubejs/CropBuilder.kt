package io.klebe.owncrops.kubejs

import io.klebe.owncrops.OwnCrops
import net.minecraft.item.FoodComponent

class CropBuilder(id: String)  {

    data class Edible(
        val amount: Int,
        val saturation: Float
    ) {
        fun toFoodComponent(): FoodComponent = FoodComponent.Builder()
            .hunger(amount)
            .saturationModifier(saturation)
            .build()
    }

    val id = OwnCrops.Identifier(id)
    val growthStage: MutableList<Double> = mutableListOf()
    var plantable: Boolean = false
    var edible: Edible? = null
    var product = ""
    var harvestOnUse = false

    fun plantable(id: String): CropBuilder {
        plantable = true
        this.product = id
        return this
    }

    fun plantable(): CropBuilder {
        plantable(id.toString())
        return this
    }

    fun addGrowthStage(height: Double): CropBuilder {
        if(!plantable) {
            OwnCrops.log.warn("Growth stage have been set, but the plant has not yet been marked as plantable. ($id)")
        }
        growthStage.add(height)

        return this
    }

    fun edible(amount: Int, saturation: Float): CropBuilder {
        edible = Edible(amount, saturation)
        return this
    }

    fun harvestOnUse(): CropBuilder {
        harvestOnUse = true
        return this
    }

}