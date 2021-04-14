package net.magic.lanterns.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class WardingLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public WardingLanternBlockEntity() {
        super(MagicLanternBlocks.WARDING_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>10){
            world.getOtherEntities(null, new Box(pos.getX()-10,pos.getY()-5,pos.getZ()-10,pos.getX()+10,pos.getY()+5,pos.getZ()+10)).forEach((entity)->{
                if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity)){
                    entity.addVelocity((double)(-pos.getX()+entity.getBlockPos().getX())/10,(double) (-pos.getY()+entity.getBlockPos().getY())/10,(double)(-pos.getZ()+entity.getBlockPos().getZ())/10);
                }
            });
            count=0;
        }
    }
}
