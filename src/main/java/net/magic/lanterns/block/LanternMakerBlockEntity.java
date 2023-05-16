package net.magic.lanterns.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class LanternMakerBlockEntity extends BlockEntity implements ImplementedInventory {
    boolean shouldLoad = true;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(10, ItemStack.EMPTY);

    public LanternMakerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MagicLanternBlocks.LANTERN_MAKER_ENTITY, blockPos, blockState);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, items);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        Inventories.writeNbt(tag, items);
        super.writeNbt(tag);
        shouldLoad = true;
    }

    public static void tick(World world, BlockPos pos, BlockState state, LanternMakerBlockEntity entity) {
        if (entity.shouldLoad && !world.isClient) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            for (int i = 0; i < entity.getItems().size(); ++i) {
                buf.writeItemStack(entity.getStack(i));
            }
            PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity) -> {
                ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "lantern_maker_items"), buf);
            });

            entity.shouldLoad =  false;
        }

        // crafting logic
        if (!world.isClient && world.getBlockState(pos.up()).getBlock() instanceof LanternBlock) {
            String recipe = entity.getItems().toString();
            if (recipe.contains("egg") && recipe.contains("melon") && recipe.contains("bone_meal") && recipe.contains("golden_apple") && recipe.contains("sugar_cane")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.LIFE_LANTERN));
            } else if (recipe.contains("glowstone") && recipe.contains("jack_o_lantern") && recipe.contains("blaze_powder") && recipe.contains("gold_ingot") && recipe.contains("fire_charge")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.FERAL_LANTERN));
            } else if (recipe.contains("diamond") && recipe.contains("golden_carrot") && recipe.contains("beetroot") && recipe.contains("rabbit_foot") && recipe.contains("honey_bottle") && recipe.contains("life_lantern")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.LOVE_LANTERN));
            } else if (recipe.contains("ghast_tear") && recipe.contains("warped_roots") && recipe.contains("flint") && recipe.contains("pufferfish") && recipe.contains("ink_sac")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.WAILING_LANTERN));
            } else if (recipe.contains("cobweb") && recipe.contains("snowball") && recipe.contains("packed_ice") && recipe.contains("quartz")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.BOREAL_LANTERN));
            } else if (recipe.contains("shulker_shell") && recipe.contains("paper") && recipe.contains("snowball") && recipe.contains("phantom_membrane")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.BRILLIANT_LANTERN));
            } else if (recipe.contains("warped_fungus") && recipe.contains("pufferfish") && recipe.contains("iron_door") && recipe.contains("obsidian")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.WARDING_LANTERN));
            } else if (recipe.contains("warding_lantern") && recipe.contains("fishing_rod") && recipe.contains("cobweb") && recipe.contains("chain")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.CONTAINING_LANTERN));
            } else if (recipe.contains("wither_rose") && recipe.contains("soul_sand") && recipe.contains("firework_star") && recipe.contains("coal")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.WITHERING_LANTERN));
            } else if (recipe.contains("phantom_membrane") && recipe.contains("soul_torch") && recipe.contains("snow_block") && recipe.contains("white_wool")) {
                entity.craft(world, new ItemStack(MagicLanternBlocks.CLOUD_LANTERN));
            } else {
                world.breakBlock(pos.up(), true);
                MagicLanternsMod.LOGGER.debug("Unknown recipe: {}", entity.getItems());
            }
        }
    }

    private void craft(World world, ItemStack stack) {
        AtomicBoolean shouldSendItems = new AtomicBoolean(false);
        getItems().forEach(itemStack -> {
            if (itemStack.getCount() == 1) {
                shouldSendItems.set(true);
            }
            if (itemStack.getCount() != 0) {
                itemStack.decrement(1);
            }
        });
        if (shouldSendItems.get()) {
            PacketByteBuf buf1 = PacketByteBufs.create();
            buf1.writeBlockPos(pos);
            for (int i = 0; i < getItems().size(); ++i) {
                buf1.writeItemStack(getStack(i));
            }
            PlayerLookup.tracking((ServerWorld) world, pos).forEach((serverPlayerEntity) -> {
                ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "lantern_maker_items"), buf1);
            });
        }
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos.up());
        PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity) -> {
            ServerPlayNetworking.send(serverPlayerEntity, new Identifier(MagicLanternsMod.MOD_ID, "lantern_craft"), buf);
        });
        world.breakBlock(pos.up(),false);
        ItemScatterer.spawn(world,pos.getX() + 0.5d,pos.getY() + 1.25d, pos.getZ() + 0.5d, stack);
    }
}
