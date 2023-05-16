package net.magic.lanterns.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FeralLanternBlockEntity extends BlockEntity {
    int count = 0;
    int totalFlares = 0;
    boolean placeAttempt = false;

    public FeralLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.FERAL_LANTERN_ENTITY, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        totalFlares = tag.getInt("totalFlares");
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putInt("totalFlares", totalFlares);
        super.writeNbt(tag);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FeralLanternBlockEntity entity) {
        ++entity.count;
        if (!world.isClient && entity.count > 20 && !entity.placeAttempt) {
            Random random = ThreadLocalRandom.current();

            if (entity.totalFlares > 100) {
                world.breakBlock(pos,false);
            }
            BlockPos check = pos.subtract(new Vec3i(40, 5, 40));
            check = check.add(random.nextInt(80), -random.nextInt(20), random.nextInt(80));
            while (check.isWithinDistance(pos, 200)) {
                entity.placeAttempt = true;
                if (world.getBlockState(check).isAir() && !(world.getBlockState(check.down()).isAir()) && world.getLightLevel(check) < 7) {
                    world.setBlockState(check, MagicLanternBlocks.SPARK.getDefaultState());
                    entity.placeAttempt = false;
                    ++entity.totalFlares;
                }
                check = check.subtract(new Vec3i(0, 1, 0));
            }
            if (!check.isWithinDistance(pos, 100)) {
                entity.placeAttempt = false;
            }
            entity.count = 0;
        }
    }
}
