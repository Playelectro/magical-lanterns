package net.magic.lanterns.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class Spark extends Block {
    public Spark() {
        super(FabricBlockSettings.of(Material.CARPET).sounds(BlockSoundGroup.STONE).breakInstantly().dropsNothing().lightLevel(15).collidable(false).nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.4,0.4,0.4,0.6,0.6,0.6);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.isClient){
            world.addParticle(ParticleTypes.FLAME,pos.getX()+0.5,pos.getY()+0.5 ,pos.getZ()+0.5,0,0.03,0);
        }
    }
}
