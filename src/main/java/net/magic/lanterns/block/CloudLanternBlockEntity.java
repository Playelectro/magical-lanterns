package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class CloudLanternBlockEntity extends BlockEntity {
    int count = 0;

    public CloudLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.CLOUD_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, CloudLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 10) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 10, pos.getY() + 0.5d - 10, pos.getZ() + 0.5d - 10, pos.getX() + 0.5d + 10, pos.getY() + 0.5d + 10, pos.getZ() + 0.5d + 10)).forEach((otherEntity) -> {
                if (otherEntity instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 5 * 20, 1));
                }
            });
            entity.count = 0;
        }
    }
}
