package io.klebe.owncrops

import com.swordglowsblue.artifice.api.Artifice
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object OwnCropsClient: ClientModInitializer {

    override fun onInitializeClient() {
        OwnCrops.log.info("Start client initialization");

        for((_, block) in OwnCrops.cropBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout())
        }

        Artifice.registerAssetPack(OwnCrops.Identifier("runtime_assets")) {
            it.setDisplayName("${OwnCrops.MODNAME} Client-Assets")
            it.setDescription("Here are the automatically generated assets")

            for((_, block) in OwnCrops.cropBlocks) block.generateAssets(it)
            for((_, item) in OwnCrops.cropBlockItems) item.generateAssets(it)
            for((_, item) in OwnCrops.cropItems) item.generateAssets(it)
        }

        OwnCrops.log.info("Client initialization completed")
    }
}