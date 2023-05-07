package net.magic.lanterns.block;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class ContainingLanternRenderer implements BlockEntityRenderer<ContainingLanternBlockEntity> {
    private final ItemRenderer itemRenderer;
    private static final ItemStack CHAIN = Items.CHAIN.getDefaultStack();

    public ContainingLanternRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ContainingLanternBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world == null) return;
        long time = world.getTime();
        int lightAbove = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up(255));

        for (int i = 30; i > 10; --i) {
            int x = ((i & 1) == 0) ? -i : i;
            double offsetX = Math.sin((time + tickDelta) / x) + 0.5;
            double offsetZ = Math.cos((time + tickDelta) / x) + 0.5;

            matrices.push();
            matrices.translate(offsetX, 0.25, offsetZ);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((time + tickDelta) * x));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((time + tickDelta) * x));
            itemRenderer.renderItem(CHAIN, ModelTransformationMode.GROUND, lightAbove, overlay, matrices, vertexConsumers, world, 0);
            matrices.pop();
        }
    }
}
