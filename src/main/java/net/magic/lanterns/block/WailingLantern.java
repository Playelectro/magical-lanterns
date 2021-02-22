package net.magic.lanterns.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class WailingLantern extends MagicLanternBase implements BlockEntityProvider {
    public WailingLantern() {
        super(FabricBlockSettings.of(Material.METAL).hardness(4f).sounds(BlockSoundGroup.LANTERN).nonOpaque().breakByTool(FabricToolTags.PICKAXES,2).lightLevel(8));
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new WailingLanternBlockEntity();
    }
}
