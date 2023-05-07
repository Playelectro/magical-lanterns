package net.magic.lanterns;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.magic.lanterns.block.*;
import net.magic.lanterns.client.BookGUI;
import net.magic.lanterns.client.BookScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class ClientMod implements ClientModInitializer {
    Random random = new Random();

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(MagicLanternBlocks.LANTERN_MAKER_ENTITY, LanternMakerRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LIFE_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.FERAL_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LOVE_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WAILING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BOREAL_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BRILLIANT_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WARDING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CONTAINING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WITHERING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CLOUD_LANTERN, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(MagicLanternBlocks.CONTAINING_LANTERN_ENTITY, ContainingLanternRenderer::new);

        // Packets S2C

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "lantern_maker_items"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            if (client.world.getBlockEntity(pos) instanceof LanternMakerBlockEntity blockEntity) {
                ItemStack stack  = buf.readItemStack();
                blockEntity.setStack(0, stack);
                for (int i = 1; i < 10; i++) {
                    stack = buf.readItemStack();
                    blockEntity.setStack(i, stack);
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "lantern_craft"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            client.world.playSound(pos.getY(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,1,1,true);
            for (int i =0; i < 20; ++i) {
                client.world.addParticle(ParticleTypes.END_ROD, pos.getX(), pos.getY(), pos.getZ(), 0.5d - random.nextDouble(), 0.5d - random.nextDouble(), 0.5d - random.nextDouble());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "life_lantern_grow"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            for (int i =0; i < 20; ++i) {
                client.world.addParticle(ParticleTypes.COMPOSTER, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "love_lantern_heart"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            for (int i =0; i < 10; ++i) {
                client.world.addParticle(ParticleTypes.HEART, pos.getX() + random.nextDouble() * 1.5, pos.getY() + random.nextDouble() * 2, pos.getZ() - 0.7 + random.nextDouble() * 1.5, 0, 0, 0);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "wailing_lantern_sounds"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            boolean scream = buf.readBoolean();
            if (!scream) {
                client.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GHAST_AMBIENT, SoundCategory.BLOCKS, 20, 1, true);
            } else {
                client.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.BLOCKS, 60, 1.3f, true);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "boreal_lantern"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            BlockPos pos1 = buf.readBlockPos();
            for(int i =0; i < 2; ++i) {
                client.world.addParticle(ParticleTypes.END_ROD, pos.getX()+random.nextDouble() , pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(),(double)(pos1.getX() -pos.getX())/10,(double)(pos1.getY()+ random.nextDouble() -pos.getY())/10,(double) (pos1.getZ() -pos.getZ()) /10);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "containing_lantern"), (client, handler, buf, responseSender) -> {
            if (client.world == null) return;
            BlockPos pos = buf.readBlockPos();
            client.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 1, 0.6f, true);
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MOD_ID, "open_the_damned_book"), (client, handler, buf, responseSender) -> {
            if (client != null) {
                // This must be executed on the render thread.
                client.execute(() -> client.setScreen(new BookScreen(new BookGUI())));
            }
        });
    }
}
