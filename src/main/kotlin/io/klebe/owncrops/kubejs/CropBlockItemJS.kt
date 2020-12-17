package io.klebe.owncrops.kubejs

import com.swordglowsblue.artifice.api.ArtificeResourcePack
import io.klebe.owncrops.OwnCrops
import net.minecraft.block.Block
import net.minecraft.item.AliasedBlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class CropBlockItemJS(block: Block, settings: Item.Settings, val id: OwnCrops.Identifier) : AliasedBlockItem(
    block,
    settings
) {
    fun generateData(pack: ArtificeResourcePack.ServerResourcePackBuilder){

    }

    fun generateAssets(pack: ArtificeResourcePack.ClientResourcePackBuilder) {
        pack.addItemModel(id) {
            it.parent(Identifier("minecraft", "item/generated"))
            it.texture("layer0", OwnCrops.Identifier("item/${id.path}"))
        }
    }
}