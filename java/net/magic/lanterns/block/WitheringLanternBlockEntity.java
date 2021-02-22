package net.magic.lanterns.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;

public class WitheringLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    public WitheringLanternBlockEntity() {
        super(MagicLanternBlocks.WITHERING_LANTERN_ENTITY);
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count>20){
            world.getOtherEntities(null, new Box(pos.getX()+0.5-7,pos.getY()+0.5-7,pos.getZ()+0.5-7,pos.getX()+0.5+7,pos.getY()+0.5+7,pos.getZ()+0.5+7)).forEach((entity)->{
                if(entity instanceof LivingEntity && !(entity instanceof PlayerEntity)){
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,3*20,1));
                }
            });
            count=0;
        }
    }
}
