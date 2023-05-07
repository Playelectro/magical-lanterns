package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class LoveLanternBlockEntity extends BlockEntity {
    int count = 0;

    public LoveLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.LOVE_LANTERN_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LoveLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 1000) {
            world.getOtherEntities(null, new Box(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5)).forEach((otherEntity) -> {
                if (otherEntity instanceof AnimalEntity animalEntity) {
                    animalEntity.setLoveTicks(20 * 10);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(otherEntity.getBlockPos());
                    PlayerLookup.tracking((ServerWorld) world, otherEntity.getBlockPos()).forEach((serverPlayerEntity) -> {
                        ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "love_lantern_heart"), buf);
                    });
                }
            });
            entity.count = 0;
        }
    }
}
