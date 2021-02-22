package net.magic.lanterns.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class CloudLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public CloudLanternBlockEntity() {
        super(MagicLanternBlocks.CLOUD_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>10){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-10,pos.getY()+0.5-10,pos.getZ()+0.5-10,pos.getX()+0.5+10,pos.getY()+0.5+10,pos.getZ()+0.5+10)).forEach((entity)->{
                if(entity instanceof LivingEntity){
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 5*20 , 1));
                }
            });
            count=0;
        }
    }
}
