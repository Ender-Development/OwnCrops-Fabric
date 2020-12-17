package io.klebe.owncrops.kubejs

import com.swordglowsblue.artifice.api.ArtificeResourcePack
import io.klebe.owncrops.OwnCrops
import io.klebe.owncrops.cropLootTable
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class CropBlockJS(val properties: CropBuilder) : CropBlock(
    FabricBlockSettings
        .of(Material.PLANT)
        .noCollision()
        .ticksRandomly()
        .breakInstantly()
        .sounds(BlockSoundGroup.CROP)
        .nonOpaque()
) {
    fun generateAssets(pack: ArtificeResourcePack.ClientResourcePackBuilder) {
        for((age, _) in properties.growthStage.withIndex()) {
            val id = OwnCrops.Identifier("${properties.id.path}_$age")
            val blockId = OwnCrops.Identifier("block/${properties.id.path}_$age")

            // Generate models
            pack.addBlockModel(id) {
                // it: ModelBuilder
                it.parent(Identifier("minecraft", "block/crop"))
                it.texture("crop", blockId)
            }
        }
        pack.addBlockState(properties.id){
            // it: BlockStateBuilder
            for((age, _) in properties.growthStage.withIndex()){
                val id = OwnCrops.Identifier("block/${properties.id.path}_$age")

                // Register variants
                it.variant("age=$age"){
                    // it: BlockStateBuilder.Variant
                    it.model(id)
                }
            }
        }
    }

    fun generateData(pack: ArtificeResourcePack.ServerResourcePackBuilder){
        pack.add(
            OwnCrops.Identifier("loot_tables/blocks/${properties.id.path}.json"),
            cropLootTable(
                properties.id.toString(),
                properties.growthStage.size - 1,
                properties.id.toString(),
                properties.product
            )
        )
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        var height: Double = 16.0;
        val age = state.get(this.getAgeProperty())
        properties.growthStage.let {
            height = if (age >= it.size) it.last() else it[age];
        }
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, height, 16.0)
    }

    override fun getMaxAge(): Int = properties.growthStage.size - 1

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult? {
        if (getAge(state) == maxAge && properties.harvestOnUse) {
            world.setBlockState(pos, withAge(0), 2)
            Block.dropStacks(state, world, pos)
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

}