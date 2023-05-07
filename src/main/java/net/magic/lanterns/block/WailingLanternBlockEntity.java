package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class WailingLanternBlockEntity extends BlockEntity {
    int count = 0;

    public WailingLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.WAILING_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WailingLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 20) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 10, pos.getY() + 0.5d - 10, pos.getZ() + 0.5d - 10, pos.getX() + 0.5d + 10, pos.getY() + 0.5d + 10, pos.getZ() + 0.5d + 10)).forEach((otherEntity) -> {
                if (otherEntity instanceof PlayerEntity playerEntity) {
                    if (!playerEntity.getBlockPos().isWithinDistance(pos,5)) {
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBlockPos(pos);
                        buf.writeBoolean(false);
                        PlayerLookup.tracking((ServerWorld) world, pos).forEach((serverPlayerEntity) -> {
                            ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "wailing_lantern_sounds"), buf);
                        });
                    } else {
                        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,5 * 20,3));
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBlockPos(pos);
                        buf.writeBoolean(true);
                        PlayerLookup.tracking((ServerWorld) world, pos).forEach((serverPlayerEntity) -> {
                            ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "wailing_lantern_sounds"), buf);
                        });
                    }
                }
            });
            entity.count = 0;
        }
    }
}
