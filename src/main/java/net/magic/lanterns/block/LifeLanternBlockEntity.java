package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

public class LifeLanternBlockEntity extends BlockEntity implements Tickable {
    int count=0;
    Random random = new Random();

    public LifeLanternBlockEntity() {
        super(MagicLanternBlocks.LIFE_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count > 30){
            BlockPos check = pos.subtract(new Vec3i(5,0,5));
            check = check.add((double) random.nextInt(10),(double) random.nextInt(5),(double) random.nextInt(10));
            while(!(world.getBlockState(check).getBlock() instanceof CropBlock) && check.isWithinDistance(pos,6d)){
                check = check.subtract(new Vec3i(0,1,0));
            }

            if(world.getBlockState(check).getBlock() instanceof CropBlock){
                ((CropBlock) world.getBlockState(check).getBlock()).applyGrowth(world,check,world.getBlockState(check));
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(check);
                PlayerLookup.tracking((ServerWorld) world,check).forEach((serverPlayerEntity)->{
                    ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "life_lantern_grow"),buf);
                });
            }
            Box box = new Box(pos.getX()+0.5-5,pos.getY()+0.5-5,pos.getZ()+0.5-5,pos.getX()+0.5+5,pos.getY()+0.5+5,pos.getZ()+0.5+5);
            world.getOtherEntities(null,box).forEach((entity)->{
               if(entity instanceof LivingEntity){
                   ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,5 * 20,2));
               }
            });
            count = 0;
        }
    }
}
