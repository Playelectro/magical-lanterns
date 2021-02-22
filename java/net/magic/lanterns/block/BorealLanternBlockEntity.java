package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;


public class BorealLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public BorealLanternBlockEntity() {
        super(MagicLanternBlocks.BOREAL_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>2){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-10,pos.getY()+0.5-10,pos.getZ()+0.5-10,pos.getX()+0.5+10,pos.getY()+0.5+10,pos.getZ()+0.5+10)).forEach((entity)->{
                if(entity instanceof LivingEntity){
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20*5,3));
                    entity.setFireTicks(0);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(pos);
                    buf.writeBlockPos(entity.getBlockPos());
                    PlayerLookup.tracking((ServerWorld) world,entity.getBlockPos()).forEach((serverPlayerEntity)->{
                        ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "boreal_lantern"),buf);
                    });
                }
            });
            count=0;
        }
    }
}
