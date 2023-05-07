package net.magic.lanterns.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class LanternMakerRenderer implements BlockEntityRenderer<LanternMakerBlockEntity> {
    private final ItemRenderer itemRenderer;

    public LanternMakerRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(LanternMakerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DefaultedList<ItemStack> items = entity.getItems();
        World world = entity.getWorld();
        if (world == null) return;
        long time = world.getTime();
        int lightAbove = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up(255));

        int i = 10;
        for (ItemStack item : items) {
            int x = ((i & 1) == 0) ? -i : i;
            double offsetY = Math.sin((time + tickDelta) / x);
            double offsetX = Math.sin((time + tickDelta) / i) + 0.5;
            double offsetZ = Math.cos((time + tickDelta) / i) + 0.5;

            matrices.push();
            matrices.translate(offsetX, 1.25 + offsetY, offsetZ);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((time + tickDelta) * i));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((time + tickDelta) * i));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((time + tickDelta) * i));
            itemRenderer.renderItem(item, ModelTransformationMode.GROUND, lightAbove, overlay, matrices, vertexConsumers, world, 0);
            matrices.pop();

            --i;
        }
    }
}
