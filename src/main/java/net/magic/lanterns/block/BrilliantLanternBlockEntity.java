package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BrilliantLanternBlockEntity extends BlockEntity {
    int count = 0;

    public BrilliantLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.BRILLIANT_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BrilliantLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 20) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 4, pos.getY() + 0.5d - 4, pos.getZ() + 0.5d - 4, pos.getX() + 0.5d + 4, pos.getY() + 0.5d + 4, pos.getZ() + 0.5d + 4)).forEach((otherEntity) -> {
                if (otherEntity instanceof LivingEntity livingEntity && !(otherEntity instanceof PlayerEntity) && !(otherEntity instanceof EnderDragonEntity)) {
                    world.spawnEntity(new ExperienceOrbEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (int) livingEntity.getMaxHealth() / 2));
                    livingEntity.remove(Entity.RemovalReason.KILLED);
                }
            });
            entity.count = 0;
        }
    }
}
