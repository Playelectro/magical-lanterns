package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LanternMaker extends BlockWithEntity {
    boolean action = false;

    public LanternMaker() {
        super(FabricBlockSettings.of(Material.STONE).requiresTool().hardness(4f).nonOpaque());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LanternMakerBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, MagicLanternBlocks.LANTERN_MAKER_ENTITY, LanternMakerBlockEntity::tick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.01,0,0.01,0.99,1,0.99);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty()) {
            for (int i = 0; i < blockEntity.size(); i++) {
                if (blockEntity.getStack(i).isEmpty() && !action) {
                    blockEntity.setStack(i, player.getStackInHand(hand).copy());
                    player.getStackInHand(hand).setCount(0);
                    action = true;
                }
            }
        } else if (player.isSneaking()) {
            for (int i = blockEntity.size() - 1; i > -1; i--) {
                if (!blockEntity.getStack(i).isEmpty()&& !action) {
                    player.setStackInHand(hand, blockEntity.getStack(i).copy());
                    blockEntity.setStack(i,ItemStack.EMPTY);
                    action = true;
                }
            }
        }
        action = false;

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        for (int i = 0; i <blockEntity.size(); ++i) {
            buf.writeItemStack(blockEntity.getStack(i));
        }
        PlayerLookup.tracking((ServerWorld) world, pos).forEach((serverPlayerEntity) -> {
            ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "lantern_maker_items"), buf);
        });
        return ActionResult.FAIL;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient && world.getBlockEntity(pos) instanceof Inventory inventory) {
            ItemScatterer.spawn(world, pos, inventory);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
