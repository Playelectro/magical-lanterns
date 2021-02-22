package net.magic.lanterns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.magic.lanterns.block.*;
import net.magic.lanterns.item.LanternCodex;
import net.magic.lanterns.item.MagicalLanternsItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;


public class MagicLanternsMod implements ModInitializer {
	public static final String MODID = "magic_lanterns";

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MODID, "general"),
			() -> new ItemStack(Blocks.SOUL_LANTERN));



	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "lantern_book"), MagicalLanternsItems.LANTERN_CODEX);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "lantern_maker"), MagicLanternBlocks.LANTERN_MAKER);
		Registry.register(Registry.ITEM, new Identifier(MODID, "lantern_maker"), new BlockItem(MagicLanternBlocks.LANTERN_MAKER, new Item.Settings().group(ITEM_GROUP)));
		MagicLanternBlocks.LANTERN_MAKER_ENTITY =  Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "lantern_maker_block_entity"), BlockEntityType.Builder.create(LanternMakerBlockEntity::new, MagicLanternBlocks.LANTERN_MAKER).build(null));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "life_lantern"), MagicLanternBlocks.LIFE_LANTERN);
		Registry.register(Registry.ITEM, new Identifier(MODID, "life_lantern"), new BlockItem(MagicLanternBlocks.LIFE_LANTERN, new Item.Settings().group(ITEM_GROUP)));
		MagicLanternBlocks.LIFE_LANTERN_ENTITY =  Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "life_lantern_block_entity"), BlockEntityType.Builder.create(LifeLanternBlockEntity::new, MagicLanternBlocks.LIFE_LANTERN).build(null));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "feral_lantern"), MagicLanternBlocks.FERAL_LANTERN);
		Registry.register(Registry.ITEM, new Identifier(MODID, "feral_lantern"), new BlockItem(MagicLanternBlocks.FERAL_LANTERN, new Item.Settings().group(ITEM_GROUP)));
		MagicLanternBlocks.FERAL_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "feral_lantern_block_entity"), BlockEntityType.Builder.create(FeralLanternBlockEntity::new, MagicLanternBlocks.FERAL_LANTERN).build(null));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "spark"), MagicLanternBlocks.SPARK);
		Registry.register(Registry.BLOCK, new Identifier(MODID, "love_lantern"), MagicLanternBlocks.LOVE_LANTERN);
		MagicLanternBlocks.LOVE_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "love_lantern_block_entity"), BlockEntityType.Builder.create(LoveLanternBlockEntity::new, MagicLanternBlocks.LOVE_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"love_lantern"), new BlockItem(MagicLanternBlocks.LOVE_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "wailing_lantern"), MagicLanternBlocks.WAILING_LANTERN);
		MagicLanternBlocks.WAILING_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "wailing_lantern_block_entity"), BlockEntityType.Builder.create(WailingLanternBlockEntity::new, MagicLanternBlocks.WAILING_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"wailing_lantern"), new BlockItem(MagicLanternBlocks.WAILING_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "boreal_lantern"), MagicLanternBlocks.BOREAL_LANTERN);
		MagicLanternBlocks.BOREAL_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "boreal_lantern_block_entity"), BlockEntityType.Builder.create(BorealLanternBlockEntity::new, MagicLanternBlocks.BOREAL_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"boreal_lantern"), new BlockItem(MagicLanternBlocks.BOREAL_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "briliant_lantern"), MagicLanternBlocks.BRILIANT_LANTERN);
		MagicLanternBlocks.BRILIANT_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "briliant_lantern_block_entity"), BlockEntityType.Builder.create(BriliantLanternBlockEntity::new, MagicLanternBlocks.BRILIANT_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"briliant_lantern"), new BlockItem(MagicLanternBlocks.BRILIANT_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "warding_lantern"), MagicLanternBlocks.WARDING_LANTERN);
		MagicLanternBlocks.WARDING_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "warding_lantern_block_entity"), BlockEntityType.Builder.create(WardingLanternBlockEntity::new, MagicLanternBlocks.WARDING_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"warding_lantern"), new BlockItem(MagicLanternBlocks.WARDING_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "containing_lantern"), MagicLanternBlocks.CONTAINING_LANTERN);
		MagicLanternBlocks.CONTAINING_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "containing_lantern_block_entity"), BlockEntityType.Builder.create(ContainingLanternBlockEntity::new, MagicLanternBlocks.CONTAINING_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"containing_lantern"), new BlockItem(MagicLanternBlocks.CONTAINING_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "withering_lantern"), MagicLanternBlocks.WITHERING_LANTERN);
		MagicLanternBlocks.WITHERING_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "withering_lantern_block_entity"), BlockEntityType.Builder.create(WitheringLanternBlockEntity::new, MagicLanternBlocks.WITHERING_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"withering_lantern"), new BlockItem(MagicLanternBlocks.WITHERING_LANTERN,new Item.Settings().group(ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(MODID, "cloud_lantern"), MagicLanternBlocks.CLOUD_LANTERN);
		MagicLanternBlocks.CLOUD_LANTERN_ENTITY =   Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "cloud_lantern_block_entity"), BlockEntityType.Builder.create(CloudLanternBlockEntity::new, MagicLanternBlocks.CLOUD_LANTERN).build(null));
		Registry.register(Registry.ITEM, new Identifier(MODID,"cloud_lantern"), new BlockItem(MagicLanternBlocks.CLOUD_LANTERN,new Item.Settings().group(ITEM_GROUP)));
	}
}
