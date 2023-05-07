package net.magic.lanterns;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.magic.lanterns.block.*;
import net.magic.lanterns.item.MagicalLanternsItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicLanternsMod implements ModInitializer {
	public static final String MOD_ID = "magic_lanterns";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ItemGroup ITEM_GROUP;

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "lantern_book"), MagicalLanternsItems.LANTERN_CODEX);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "lantern_maker"), MagicLanternBlocks.LANTERN_MAKER);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "lantern_maker"), new BlockItem(MagicLanternBlocks.LANTERN_MAKER, new Item.Settings()));
		MagicLanternBlocks.LANTERN_MAKER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "lantern_maker_block_entity"), FabricBlockEntityTypeBuilder.create(LanternMakerBlockEntity::new, MagicLanternBlocks.LANTERN_MAKER).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "spark"), MagicLanternBlocks.SPARK);

		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "boreal_lantern"), MagicLanternBlocks.BOREAL_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"boreal_lantern"), new BlockItem(MagicLanternBlocks.BOREAL_LANTERN, new Item.Settings()));
		MagicLanternBlocks.BOREAL_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "boreal_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(BorealLanternBlockEntity::new, MagicLanternBlocks.BOREAL_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "brilliant_lantern"), MagicLanternBlocks.BRILLIANT_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"brilliant_lantern"), new BlockItem(MagicLanternBlocks.BRILLIANT_LANTERN, new Item.Settings()));
		MagicLanternBlocks.BRILLIANT_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "brilliant_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(BrilliantLanternBlockEntity::new, MagicLanternBlocks.BRILLIANT_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "cloud_lantern"), MagicLanternBlocks.CLOUD_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"cloud_lantern"), new BlockItem(MagicLanternBlocks.CLOUD_LANTERN, new Item.Settings()));
		MagicLanternBlocks.CLOUD_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "cloud_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(CloudLanternBlockEntity::new, MagicLanternBlocks.CLOUD_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "containing_lantern"), MagicLanternBlocks.CONTAINING_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"containing_lantern"), new BlockItem(MagicLanternBlocks.CONTAINING_LANTERN, new Item.Settings()));
		MagicLanternBlocks.CONTAINING_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "containing_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(ContainingLanternBlockEntity::new, MagicLanternBlocks.CONTAINING_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "feral_lantern"), MagicLanternBlocks.FERAL_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "feral_lantern"), new BlockItem(MagicLanternBlocks.FERAL_LANTERN, new Item.Settings()));
		MagicLanternBlocks.FERAL_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "feral_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(FeralLanternBlockEntity::new, MagicLanternBlocks.FERAL_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "life_lantern"), MagicLanternBlocks.LIFE_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "life_lantern"), new BlockItem(MagicLanternBlocks.LIFE_LANTERN, new Item.Settings()));
		MagicLanternBlocks.LIFE_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "life_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(LifeLanternBlockEntity::new, MagicLanternBlocks.LIFE_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "love_lantern"), MagicLanternBlocks.LOVE_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"love_lantern"), new BlockItem(MagicLanternBlocks.LOVE_LANTERN, new Item.Settings()));
		MagicLanternBlocks.LOVE_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "love_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(LoveLanternBlockEntity::new, MagicLanternBlocks.LOVE_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "wailing_lantern"), MagicLanternBlocks.WAILING_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"wailing_lantern"), new BlockItem(MagicLanternBlocks.WAILING_LANTERN, new Item.Settings()));
		MagicLanternBlocks.WAILING_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "wailing_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(WailingLanternBlockEntity::new, MagicLanternBlocks.WAILING_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "warding_lantern"), MagicLanternBlocks.WARDING_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"warding_lantern"), new BlockItem(MagicLanternBlocks.WARDING_LANTERN, new Item.Settings()));
		MagicLanternBlocks.WARDING_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "warding_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(WardingLanternBlockEntity::new, MagicLanternBlocks.WARDING_LANTERN).build(null));
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "withering_lantern"), MagicLanternBlocks.WITHERING_LANTERN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID,"withering_lantern"), new BlockItem(MagicLanternBlocks.WITHERING_LANTERN, new Item.Settings()));
		MagicLanternBlocks.WITHERING_LANTERN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "withering_lantern_block_entity"), FabricBlockEntityTypeBuilder.create(WitheringLanternBlockEntity::new, MagicLanternBlocks.WITHERING_LANTERN).build(null));

		ITEM_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, "general"))
				.displayName(Text.literal("Magic Lanterns"))
				.icon(() -> new ItemStack(Blocks.SOUL_LANTERN))
				.entries((context, entries) -> {
					entries.add(MagicalLanternsItems.LANTERN_CODEX);
					entries.add(MagicLanternBlocks.LANTERN_MAKER);
					entries.add(MagicLanternBlocks.SPARK);

					entries.add(MagicLanternBlocks.BOREAL_LANTERN);
					entries.add(MagicLanternBlocks.BRILLIANT_LANTERN);
					entries.add(MagicLanternBlocks.CLOUD_LANTERN);
					entries.add(MagicLanternBlocks.CONTAINING_LANTERN);
					entries.add(MagicLanternBlocks.FERAL_LANTERN);
					entries.add(MagicLanternBlocks.LIFE_LANTERN);
					entries.add(MagicLanternBlocks.LOVE_LANTERN);
					entries.add(MagicLanternBlocks.WAILING_LANTERN);
					entries.add(MagicLanternBlocks.WARDING_LANTERN);
					entries.add(MagicLanternBlocks.WITHERING_LANTERN);
				}).build();
	}
}
