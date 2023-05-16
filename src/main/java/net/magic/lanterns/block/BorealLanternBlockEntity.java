package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BorealLanternBlockEntity extends BlockEntity {
    int count = 0;

    public BorealLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.BOREAL_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BorealLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 2) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 10, pos.getY() + 0.5d - 10, pos.getZ() + 0.5d - 10, pos.getX() + 0.5d + 10, pos.getY() + 0.5d + 10, pos.getZ() + 0.5d + 10)).forEach((otherEntity) -> {
                if(otherEntity instanceof LivingEntity livingEntity){
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20 * 5,3));
                    otherEntity.setFireTicks(0);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(pos);
                    buf.writeBlockPos(otherEntity.getBlockPos());
                    PlayerLookup.tracking((ServerWorld) world, otherEntity.getBlockPos()).forEach((serverPlayerEntity) -> {
                        ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "boreal_lantern"), buf);
                    });
                }
            });
            entity.count = 0;
        }
    }
}
