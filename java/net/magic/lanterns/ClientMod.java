package net.magic.lanterns;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.magic.lanterns.block.*;
import net.magic.lanterns.client.BookGUI;
import net.magic.lanterns.client.BookScreen;
import net.minecraft.client.render.RenderLayer;
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
        BlockEntityRendererRegistry.INSTANCE.register(MagicLanternBlocks.LANTERN_MAKER_ENTITY, LanternMakerRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LIFE_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.FERAL_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.LOVE_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WAILING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BOREAL_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.BRILIANT_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WARDING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CONTAINING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.WITHERING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MagicLanternBlocks.CLOUD_LANTERN, RenderLayer.getCutout());
        BlockEntityRendererRegistry.INSTANCE.register(MagicLanternBlocks.CONTAINING_LANTERN_ENTITY, ContainingLanternRenderer::new);



        //Packets S2C

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "lantern_maker_items"), (client, handler, buf, responseSender) -> {
                BlockPos pos = buf.readBlockPos();
                if(client.world.getBlockEntity(pos) instanceof LanternMakerBlockEntity) {
                    ItemStack stack  = buf.readItemStack();
                    ((LanternMakerBlockEntity) client.world.getBlockEntity(pos)).setStack(0,stack);
                    for(int i = 1; i < 10; i++){
                        stack  = buf.readItemStack();
                        ((LanternMakerBlockEntity) client.world.getBlockEntity(pos)).setStack(i,stack);
                    }
                }

        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "lantern_craft"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            client.world.playSound(pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,1,1,true);
            for(int i =0; i < 20; ++i)
            client.world.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY(),pos.getZ(),0.5d - random.nextDouble(),0.5d - random.nextDouble(),0.5d - random.nextDouble());
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "life_lantern_grow"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            for(int i =0; i < 20; ++i)
                client.world.addParticle(ParticleTypes.COMPOSTER,pos.getX() + random.nextDouble(),pos.getY()+ random.nextDouble(),pos.getZ()+ random.nextDouble(),0,0,0);
        });
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "love_lantern_heart"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            for(int i =0; i < 10; ++i)
                client.world.addParticle(ParticleTypes.HEART,pos.getX()+ random.nextDouble() * 1.5,pos.getY()+ random.nextDouble() * 2,pos.getZ()-0.7+ random.nextDouble() * 1.5,0,0,0);
        });
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "wailing_lantern_sounds"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            boolean scream = buf.readBoolean();
            if(!scream) {
                client.world.playSound(pos, SoundEvents.ENTITY_GHAST_AMBIENT, SoundCategory.BLOCKS, 20, 1, true);
            }else {
                client.world.playSound(pos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.BLOCKS, 60, 1.3f, true);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "boreal_lantern"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            BlockPos pos1 = buf.readBlockPos();
            for(int i =0; i < 2; ++i) {
                client.world.addParticle(ParticleTypes.END_ROD, pos.getX()+random.nextDouble() , pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(),(double)(pos1.getX() -pos.getX())/10,(double)(pos1.getY()+ random.nextDouble() -pos.getY())/10,(double) (pos1.getZ() -pos.getZ()) /10);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "containing_lantern"), (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            client.world.playSound(pos,SoundEvents.BLOCK_CHAIN_BREAK,SoundCategory.BLOCKS,60,0.6f,true);
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MagicLanternsMod.MODID, "open_the_damned_book"), (client, handler, buf, responseSender) -> {
            client.openScreen(new BookScreen(new BookGUI()));
        });
    }
}
