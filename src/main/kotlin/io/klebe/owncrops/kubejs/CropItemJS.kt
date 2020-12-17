package io.klebe.owncrops.kubejs

import com.swordglowsblue.artifice.api.ArtificeResourcePack
import io.klebe.owncrops.OwnCrops
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class CropItemJS(settings: Item.Settings, val id: OwnCrops.Identifier) : Item(settings){
    fun generateData(pack: ArtificeResourcePack.ServerResourcePackBuilder) {

    }

    fun generateAssets(pack: ArtificeResourcePack.ClientResourcePackBuilder) {
        pack.addItemModel(id) {
            it.parent(Identifier("minecraft", "item/generated"))
            it.texture("layer0", OwnCrops.Identifier("item/${id.path}"))
        }
    }
}