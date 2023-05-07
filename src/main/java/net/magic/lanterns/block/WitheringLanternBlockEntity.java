package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class WitheringLanternBlockEntity extends BlockEntity {
    int count = 0;

    public WitheringLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.WITHERING_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WitheringLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 20) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 7, pos.getY() + 0.5d - 7, pos.getZ() + 0.5d - 7, pos.getX() + 0.5d + 7, pos.getY() + 0.5d + 7, pos.getZ() + 0.5d + 7)).forEach((otherEntity) -> {
                if (otherEntity instanceof LivingEntity livingEntity && !(otherEntity instanceof PlayerEntity)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 3 * 20, 1));
                }
            });
            entity.count = 0;
        }
    }
}
