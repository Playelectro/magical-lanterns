package net.magic.lanterns.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public class ContainingLanternRenderer extends BlockEntityRenderer<ContainingLanternBlockEntity> {
    private static final ItemStack chain = new ItemStack(Items.CHAIN);
    public ContainingLanternRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ContainingLanternBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up(255));
        for(int i = 30; i > 10; i--) {
            matrices.push();
            int x = (i%2==0) ? -i : i;
            double offsetx = Math.sin((entity.getWorld().getTime() + tickDelta) / x)+0.5;
            double offsetz = Math.cos((entity.getWorld().getTime() + tickDelta) /  x) + 0.5;
            matrices.translate( offsetx, 0.25, offsetz);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)* x));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)* x));
            MinecraftClient.getInstance().getItemRenderer().renderItem(chain, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers);

            matrices.pop();
        }
    }
}
