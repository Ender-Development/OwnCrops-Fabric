package io.klebe.owncrops.kubejs

import com.google.gson.JsonObject
import dev.latvian.kubejs.block.BlockBuilder
import dev.latvian.kubejs.util.BuilderBase
import io.klebe.owncrops.OwnCrops
import net.minecraft.item.FoodComponent
import net.minecraft.util.math.Direction


class CropBuilder(id: String) : BuilderBase(id) {

    enum class ModelType{
        X, HASH, CUSTOM
    }

    data class Edible(
        val amount: Int,
        val saturation: Float
    ) {
        fun toFoodComponent(): FoodComponent = FoodComponent.Builder()
            .hunger(amount)
            .saturationModifier(saturation)
            .build()
    }

    var growthStage: MutableList<Double> = mutableListOf()
    var plantable: Boolean = false
    var edible: Edible? = null
    var modelType: ModelType = ModelType.HASH

    fun plantable(): CropBuilder {
        plantable = true
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

    fun modelX(): CropBuilder {
        modelType = ModelType.X
        return this
    }

    fun modelHash(): CropBuilder {
        modelType = ModelType.HASH
        return this
    }

    fun customModel(): CropBuilder {
        modelType = ModelType.CUSTOM
        return this
    }

    override fun getType(): String = "crop"
}