package io.klebe.owncrops.kubejs

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class CropBlockJS(properties: CropBuilder) : CropBlock(
    FabricBlockSettings
        .of(Material.PLANT)
        .noCollision()
        .ticksRandomly()
        .breakInstantly()
        .sounds(BlockSoundGroup.CROP)
) {
    val properties = properties

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
        state: BlockState?,
        world: World,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult? {
        if (getAge(state) == maxAge) {
            world.setBlockState(pos, withAge(0), 2)
            Block.dropStacks(state, world, pos)
            return ActionResult.CONSUME
        }
        return ActionResult.PASS
    }
}