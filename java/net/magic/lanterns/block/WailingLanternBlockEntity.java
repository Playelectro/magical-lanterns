package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class WailingLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public WailingLanternBlockEntity() {
        super(MagicLanternBlocks.WAILING_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>20){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-10,pos.getY()+0.5-10,pos.getZ()+0.5-10,pos.getX()+0.5+10,pos.getY()+0.5+10,pos.getZ()+0.5+10)).forEach((entity)->{
                if(entity instanceof PlayerEntity){
                    if(!entity.getBlockPos().isWithinDistance(pos,5)) {
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBlockPos(pos);
                        buf.writeBoolean(false);
                        PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity)->{
                            ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "wailing_lantern_sounds"),buf);
                        });
                    }else{
                        ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,5 * 20,3));
                        PacketByteBuf buf1 = PacketByteBufs.create();
                        buf1.writeBlockPos(pos);
                        buf1.writeBoolean(true);
                        PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity)->{
                            ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "wailing_lantern_sounds"),buf1);
                        });
                    }
                }
            });
            count=0;
        }
    }
}
