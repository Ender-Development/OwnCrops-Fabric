package io.klebe.owncrops

import dev.latvian.kubejs.script.ScriptType
import io.klebe.owncrops.kubejs.*
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object OwnCrops : ModInitializer {
    const val MODID = "owncrops"
    const val MODNAME = "Own Crops"

    val crops: MutableMap<String, CropBuilder> = mutableMapOf()

    val cropBlocks: MutableMap<String, CropBlockJS> = mutableMapOf()
    val cropBlockItems: MutableMap<String, CropBlockItemJS> = mutableMapOf()
    val cropItems: MutableMap<String, CropItemJS> = mutableMapOf()

    val log = LogManager.getLogger(MODID)

    fun registerItem(item: Item, id: String){
        Registry.register(Registry.ITEM, Identifier(MODID, id), item)
    }

    fun registerBlock(block: Block, id: String){
        Registry.register(Registry.BLOCK, Identifier(MODID, id), block)
    }

    override fun onInitialize() {
        log.info("Start initialization")

        CropRegistryEventJS.post(ScriptType.STARTUP, "${MODID}.crop.registry")

        log.info("Building crops")
        for ((id, crop) in crops){
            if(crop.plantable){
                val block = CropBlockJS(crop)
                val itemSettings = Item.Settings()
                cropBlocks[id] = block
                crop.edible?.let {
                    itemSettings.food(it.toFoodComponent())
                }
                cropBlockItems[id] = CropBlockItemJS(block, itemSettings)
            } else {
                val itemSettings = Item.Settings()
                crop.edible?.let {
                    itemSettings.food(it.toFoodComponent())
                }
                cropItems[id] = CropItemJS(itemSettings)
            }
        }

        log.info("Register crops")
        for((id, block) in cropBlocks) registerBlock(block, id)
        for((id, item) in cropBlockItems) registerItem(item, id)
        for((id, item) in cropItems) registerItem(item, id)

        log.info("Initialization completed")
    }

}
