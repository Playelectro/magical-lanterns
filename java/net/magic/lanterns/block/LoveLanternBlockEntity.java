package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class LoveLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public LoveLanternBlockEntity() {
        super(MagicLanternBlocks.LOVE_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count > 1000){
            world.getOtherEntities(null, new Box(pos.getX()-5,pos.getY()-5,pos.getZ()-5,pos.getX()+5,pos.getY()+5,pos.getZ()+5)).forEach((entity)->{
                if(entity instanceof AnimalEntity){
                    ((AnimalEntity) entity).setLoveTicks(20*10);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(entity.getBlockPos());
                    PlayerLookup.tracking((ServerWorld) world,entity.getBlockPos()).forEach((serverPlayerEntity)->{
                        ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "love_lantern_heart"),buf);
                    });
                }
            });
            count=0;
        }
    }
}
