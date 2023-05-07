package net.magic.lanterns.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainingLantern extends MagicLanternBase {
   public ContainingLantern() {
        super(FabricBlockSettings.of(Material.METAL).requiresTool().hardness(4f).lightLevel(15).nonOpaque().sounds(BlockSoundGroup.LANTERN));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ContainingLanternBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, MagicLanternBlocks.CONTAINING_LANTERN_ENTITY, ContainingLanternBlockEntity::tick);
    }
}
