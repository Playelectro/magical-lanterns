package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

public class FeralLanternBlockEntity extends BlockEntity implements Tickable {
    int count = 0;
    int totalFlares = 0;
    boolean placeAttempt = false;
    Random random = new Random();
    public FeralLanternBlockEntity() {
        super(MagicLanternBlocks.FERAL_LANTERN_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("totalFlares", totalFlares);
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        totalFlares = tag.getInt("totalFlares");
    }

    @Override
    public void tick() {
        count++;
        if(!world.isClient && count > 20 && !placeAttempt){
            if(totalFlares>100){
                world.breakBlock(pos,false);
            }
            BlockPos check = pos.subtract(new Vec3i(40,5,40));
            check = check.add( random.nextInt(80), -random.nextInt(20),random.nextInt(80));
            while(check.isWithinDistance(pos, 200)) {
                placeAttempt = true;
                if (world.getBlockState(check).isAir() && !(world.getBlockState(check.down()).isAir()) && world.getLightLevel(check) < 7) {
                    world.setBlockState(check, MagicLanternBlocks.SPARK.getDefaultState());
                    placeAttempt = false;
                    totalFlares++;
                }
                check = check.subtract(new Vec3i(0,1,0));
            }
            if(!check.isWithinDistance(pos, 100)){
                placeAttempt = false;
            }
            count = 0;
        }
    }
}
