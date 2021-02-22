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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class LanternMakerBlockEntity extends BlockEntity implements ImplementedInventory, Tickable {

    boolean shouldLoad = true;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(10, ItemStack.EMPTY);

    public LanternMakerBlockEntity() {
        super(MagicLanternBlocks.LANTERN_MAKER_ENTITY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag,items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag,items);
        shouldLoad = true;
        return super.toTag(tag);
    }

    @Override
    public void tick() {
        if(shouldLoad && !world.isClient){
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            for(int i = 0; i <getItems().size(); ++i) {
                buf.writeItemStack(getStack(i));
            }
            PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity) -> {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayerEntity, new Identifier(MagicLanternsMod.MODID, "lantern_maker_items"), buf);
            });

            shouldLoad =  false;
        }


        //crafting logic
        if(!world.isClient &&world.getBlockState(pos.up()).getBlock() instanceof LanternBlock){
            String recipe = getItems().toString();
            if(recipe.contains("egg")&&recipe.contains("melon")&&recipe.contains("bone_meal")&&recipe.contains("golden_apple")&&recipe.contains("sugar_cane")){
                craft(world, new ItemStack(MagicLanternBlocks.LIFE_LANTERN.asItem()));
            }else if(recipe.contains("glowstone")&&recipe.contains("jack_o_lantern")&&recipe.contains("blaze_powder")&&recipe.contains("gold_ingot")&&recipe.contains("fire_charge")){
                craft(world, new ItemStack(MagicLanternBlocks.FERAL_LANTERN.asItem()));
            }else if(recipe.contains("diamond")&&recipe.contains("golden_carrot")&&recipe.contains("beetroot")&&recipe.contains("rabbit_foot")&&recipe.contains("honey_bottle")&&recipe.contains("life_lantern")){
                craft(world,new ItemStack(MagicLanternBlocks.LOVE_LANTERN.asItem()));
            }else if(recipe.contains("ghast_tear")&&recipe.contains("warped_roots")&&recipe.contains("flint")&&recipe.contains("pufferfish")&&recipe.contains("ink_sac")){
                craft(world,new ItemStack(MagicLanternBlocks.WAILING_LANTERN.asItem()));
            }else if(recipe.contains("cobweb")&&recipe.contains("snowball")&&recipe.contains("packed_ice")&&recipe.contains("quartz")){
                craft(world,new ItemStack(MagicLanternBlocks.BOREAL_LANTERN.asItem()));
            }else if(recipe.contains("shulker_shell")&& recipe.contains("paper")&&recipe.contains("snowball")&&recipe.contains("phantom_membrane")){
                craft(world,new ItemStack(MagicLanternBlocks.BRILIANT_LANTERN.asItem()));
            }else if(recipe.contains("warped_fungus")&& recipe.contains("pufferfish")&&recipe.contains("iron_door")&&recipe.contains("obsidian")){
                craft(world,new ItemStack(MagicLanternBlocks.WARDING_LANTERN.asItem()));
            }else if(recipe.contains("warding_lantern")&& recipe.contains("fishing_rod")&&recipe.contains("cobweb")&&recipe.contains("chain")){
            craft(world,new ItemStack(MagicLanternBlocks.CONTAINING_LANTERN.asItem()));
            }else if(recipe.contains("wither_rose")&& recipe.contains("soul_sand")&&recipe.contains("firework_star")&&recipe.contains("coal")){
                craft(world,new ItemStack(MagicLanternBlocks.WITHERING_LANTERN.asItem()));
            }else if(recipe.contains("phantom_membrane")&& recipe.contains("soul_torch")&&recipe.contains("snow_block")&&recipe.contains("white_wool")){
                craft(world,new ItemStack(MagicLanternBlocks.CLOUD_LANTERN.asItem()));
            }
            else{
                world.breakBlock(pos.up(), true);
                System.out.println(getItems());
            }
        }
    }

    private void craft(World world, ItemStack stack){
        AtomicBoolean shouldSendItems = new AtomicBoolean(false);
        getItems().forEach(itemStack -> {
            if(itemStack.getCount()==1) {
                shouldSendItems.set(true);
            }
            if(itemStack.getCount()!=0)
            itemStack.decrement(1);
        });
        if(shouldSendItems.get()) {
            PacketByteBuf buf1 = PacketByteBufs.create();
            buf1.writeBlockPos(pos);
            for (int i = 0; i < getItems().size(); ++i) {
                buf1.writeItemStack(getStack(i));
            }
            PlayerLookup.tracking((ServerWorld) world, pos).forEach((serverPlayerEntity) -> {
                ServerPlayNetworking.send((ServerPlayerEntity) serverPlayerEntity, new Identifier(MagicLanternsMod.MODID, "lantern_maker_items"), buf1);
            });
        }
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos.up());
        PlayerLookup.tracking((ServerWorld) world,pos).forEach((serverPlayerEntity) -> {
            ServerPlayNetworking.send((ServerPlayerEntity) serverPlayerEntity, new Identifier(MagicLanternsMod.MODID, "lantern_craft"), buf);
        });
        world.breakBlock(pos.up(),false);
        ItemScatterer.spawn(world,pos.getX()+0.5,pos.getY()+1.25, pos.getZ()+0.5, stack);
    }
}
