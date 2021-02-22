package net.magic.lanterns.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class FeralLantern extends MagicLanternBase implements BlockEntityProvider {
    public FeralLantern() {
        super(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.LANTERN).hardness(4f).lightLevel(15).breakByTool(FabricToolTags.PICKAXES,2));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new FeralLanternBlockEntity();
    }

}
