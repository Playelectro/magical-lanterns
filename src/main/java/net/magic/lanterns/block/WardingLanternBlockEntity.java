package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class WardingLanternBlockEntity extends BlockEntity {
    int count = 0;

    public WardingLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.WARDING_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WardingLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 10) {
            world.getOtherEntities(null, new Box(pos.getX() - 10, pos.getY() - 5, pos.getZ() - 10, pos.getX() + 10, pos.getY() + 5, pos.getZ() + 10)).forEach((otherEntity) -> {
                if (otherEntity instanceof LivingEntity && !(otherEntity instanceof PlayerEntity)) {
                    otherEntity.addVelocity((double) (-pos.getX() + otherEntity.getBlockPos().getX()) / 10, (double) (-pos.getY() + otherEntity.getBlockPos().getY()) / 10, (double) (-pos.getZ() + otherEntity.getBlockPos().getZ()) / 10);
                }
            });
            entity.count = 0;
        }
    }
}
