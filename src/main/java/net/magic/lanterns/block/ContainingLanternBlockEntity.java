package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ContainingLanternBlockEntity extends BlockEntity {
    int count = 0;
    public ContainingLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.CONTAINING_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ContainingLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 5) {
            world.getOtherEntities(null, new Box(pos.getX() + 0.5d - 10, pos.getY() + 0.5d - 4, pos.getZ() + 0.5d - 10, pos.getX() + 0.5d + 10, pos.getY() + 0.5d + 4, pos.getZ() + 0.5d + 10)).forEach((otherEntity) -> {
                if (otherEntity instanceof LivingEntity && !(otherEntity instanceof PlayerEntity) && !otherEntity.getBlockPos().isWithinDistance(pos,6)) {
                    if (world.getBlockState(pos.up()).isAir()) {
                        otherEntity.teleport(pos.getX(), pos.getY() + 1, pos.getZ());
                    } else {
                        otherEntity.teleport(pos.getX(), pos.getY() - 1, pos.getZ());
                    }

                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(pos);
                    PlayerLookup.tracking((ServerWorld) world, otherEntity.getBlockPos()).forEach((serverPlayerEntity) -> {
                        ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "containing_lantern"), buf);
                    });
                }
            });
            entity.count = 0;
        }
    }
}
