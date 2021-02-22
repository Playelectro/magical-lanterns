package net.magic.lanterns.item;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.magic.lanterns.client.BookGUI;
import net.magic.lanterns.client.BookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LanternCodex extends Item {
    public LanternCodex() {
        super(new Item.Settings().group(MagicLanternsMod.ITEM_GROUP).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //I AM DONE WITH THIS, THE SITE LIES, TRUST NOTHING
        if(world.isClient) return TypedActionResult.success(user.getStackInHand(hand));

        ServerPlayNetworking.send((ServerPlayerEntity) user,new Identifier(MagicLanternsMod.MODID, "open_the_damned_book"),PacketByteBufs.create());

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
