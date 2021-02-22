package net.magic.lanterns.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class BriliantLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public BriliantLanternBlockEntity() {
        super(MagicLanternBlocks.BRILIANT_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>20){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-4,pos.getY()+0.5-4,pos.getZ()+0.5-4,pos.getX()+0.5+4,pos.getY()+0.5+4,pos.getZ()+0.5+4)).forEach((entity)->{
                if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity)&& !(entity instanceof EnderDragonEntity)){
                    world.spawnEntity(new ExperienceOrbEntity(world,entity.getX(),entity.getY(),entity.getZ(),(int) ((((LivingEntity) entity).getMaxHealth()) / 2)));
                    entity.remove();
                }
            });
            count=0;
        }
    }
}
