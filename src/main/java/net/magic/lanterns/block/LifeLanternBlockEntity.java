package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LifeLanternBlockEntity extends BlockEntity {
    int count=0;

    public LifeLanternBlockEntity(BlockPos pos, BlockState state) {
        super(MagicLanternBlocks.LIFE_LANTERN_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LifeLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 30) {
            Random random = ThreadLocalRandom.current();

            BlockPos check = pos.subtract(new Vec3i(5,0,5));
            check = check.add(random.nextInt(10), random.nextInt(5), random.nextInt(10));
            while (!(world.getBlockState(check).getBlock() instanceof CropBlock) && check.isWithinDistance(pos,6d)) {
                check = check.subtract(new Vec3i(0,1,0));
            }

            if (world.getBlockState(check).getBlock() instanceof CropBlock cropBlock) {
                cropBlock.applyGrowth(world, check, world.getBlockState(check));
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(check);
                PlayerLookup.tracking((ServerWorld) world, check).forEach((serverPlayerEntity) -> {
                    ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "life_lantern_grow"), buf);
                });
            }
            Box box = new Box(pos.getX() + 0.5d - 5,pos.getY() + 0.5d - 5,pos.getZ() + 0.5d - 5,pos.getX() + 0.5d + 5,pos.getY() + 0.5d + 5,pos.getZ() + 0.5d + 5);
            world.getOtherEntities(null, box).forEach((otherEntity) -> {
               if (otherEntity instanceof LivingEntity livingEntity) {
                   livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,5 * 20,2));
               }
            });
            entity.count = 0;
        }
    }
}
