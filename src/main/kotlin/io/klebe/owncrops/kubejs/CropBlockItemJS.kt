package io.klebe.owncrops.kubejs

import net.minecraft.block.Block
import net.minecraft.item.AliasedBlockItem
import net.minecraft.item.Item

class CropBlockItemJS(block: Block, settings: Item.Settings) : AliasedBlockItem(
    block,
    settings
) {
}