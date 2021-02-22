package net.magic.lanterns.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;

public class MagicLanternBlocks {
    public static final Block LANTERN_MAKER = new LanternMaker();
    public static BlockEntityType<LanternMakerBlockEntity> LANTERN_MAKER_ENTITY;
    public static final Block LIFE_LANTERN = new LifeLantern();
    public static BlockEntityType<LifeLanternBlockEntity> LIFE_LANTERN_ENTITY;
    public static final Block FERAL_LANTERN = new FeralLantern();
    public static BlockEntityType<FeralLanternBlockEntity> FERAL_LANTERN_ENTITY;
    public static final Block SPARK = new Spark();
    public static final Block LOVE_LANTERN = new LoveLantern();
    public static BlockEntityType<LoveLanternBlockEntity> LOVE_LANTERN_ENTITY;
    public static final Block WAILING_LANTERN = new WailingLantern();
    public static BlockEntityType<WailingLanternBlockEntity> WAILING_LANTERN_ENTITY;
    public static final Block BOREAL_LANTERN = new BorealLantern();
    public static BlockEntityType<BorealLanternBlockEntity> BOREAL_LANTERN_ENTITY;
    public static final Block BRILIANT_LANTERN = new BriliantLantern();
    public static BlockEntityType<BriliantLanternBlockEntity> BRILIANT_LANTERN_ENTITY;
    public static final Block WARDING_LANTERN = new WardingLantern();
    public static BlockEntityType<WardingLanternBlockEntity> WARDING_LANTERN_ENTITY;
    public static final Block CONTAINING_LANTERN = new ContainingLantern();
    public static BlockEntityType<ContainingLanternBlockEntity> CONTAINING_LANTERN_ENTITY;
    public static final Block WITHERING_LANTERN = new WitheringLantern();
    public static BlockEntityType<WitheringLanternBlockEntity> WITHERING_LANTERN_ENTITY;
    public static final Block CLOUD_LANTERN = new CloudLantern();
    public static BlockEntityType<CloudLanternBlockEntity> CLOUD_LANTERN_ENTITY;
    public static final Block LANTERNIUM_ORE = new Block(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES,2).hardness(4f));
}
