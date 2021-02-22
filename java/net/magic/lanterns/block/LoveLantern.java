package net.magic.lanterns.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class LoveLantern extends MagicLanternBase implements BlockEntityProvider {
    public LoveLantern() {
        super(FabricBlockSettings.of(Material.METAL).hardness(4f).lightLevel(15).breakByTool(FabricToolTags.PICKAXES,2).sounds(BlockSoundGroup.LANTERN));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LoveLanternBlockEntity();
    }
}
