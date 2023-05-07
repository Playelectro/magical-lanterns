package net.magic.lanterns.item;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic.lanterns.MagicLanternsMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LanternCodex extends Item {
    public LanternCodex() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //I AM DONE WITH THIS, THE SITE LIES, TRUST NOTHING
        if (world.isClient) return TypedActionResult.success(user.getStackInHand(hand));

        ServerPlayNetworking.send((ServerPlayerEntity) user, new Identifier(MagicLanternsMod.MOD_ID, "open_the_damned_book"), PacketByteBufs.create());

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
