package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class ContainingLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public ContainingLanternBlockEntity() {
        super(MagicLanternBlocks.CONTAINING_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>5){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-6,pos.getY()+0.5-6,pos.getZ()+0.5-6,pos.getX()+0.5+6,pos.getY()+0.5+6,pos.getZ()+0.5+6)).forEach((entity)->{
                if(entity instanceof LivingEntity&& !(entity instanceof PlayerEntity)&&!entity.getBlockPos().isWithinDistance(pos,4)){
                    if(world.getBlockState(pos.up()).isAir()) {
                        entity.teleport(pos.getX(), pos.getY() + 1, pos.getZ());
                    }else
                    {
                        entity.teleport(pos.getX(), pos.getY() -1, pos.getZ());
                    }
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBlockPos(pos);
                    PlayerLookup.tracking((ServerWorld) world,entity.getBlockPos()).forEach((serverPlayerEntity)->{
                        ServerPlayNetworking.send(serverPlayerEntity,new Identifier(MagicLanternsMod.MODID, "containing_lantern"),buf);
                    });
                }
            });
            count=0;
        }
    }
}
