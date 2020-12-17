package io.klebe.owncrops

import com.swordglowsblue.artifice.api.Artifice
import com.swordglowsblue.artifice.api.ArtificeResourcePack
import com.swordglowsblue.artifice.api.util.Processor
import dev.latvian.kubejs.script.ScriptType
import io.klebe.owncrops.kubejs.*
import net.fabricmc.api.ModInitializer
import net.minecraft.item.Item
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

    class Identifier(id: String): net.minecraft.util.Identifier(MODID, id)

    fun registerItem(item: Item, id: String){
        Registry.register(Registry.ITEM, Identifier(id), item)
    }

    fun registerBlock(block: CropBlockJS, id: String){
        Registry.register(Registry.BLOCK, Identifier(id), block)
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
                cropBlockItems[id] = CropBlockItemJS(block, itemSettings, crop.id)
            } else {
                val itemSettings = Item.Settings()
                crop.edible?.let {
                    itemSettings.food(it.toFoodComponent())
                }
                cropItems[id] = CropItemJS(itemSettings, crop.id)
            }
        }

        log.info("Register crops")
        for((id, block) in cropBlocks) registerBlock(block, id)
        for((id, item) in cropBlockItems) registerItem(item, id)
        for((id, item) in cropItems) registerItem(item, id)


        Artifice.registerDataPack(Identifier("runtime_data")){
            it.setDisplayName("$MODNAME Datapack")
            it.setDescription("Here are the automatically generated datas")

            for((_, block) in cropBlocks) block.generateData(it)
            for((_, item) in cropBlockItems) item.generateData(it)
            for((_, item) in cropItems) item.generateData(it)
        }

        log.info("Initialization completed")
    }

}
