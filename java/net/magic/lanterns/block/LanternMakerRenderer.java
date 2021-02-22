package net.magic.lanterns.block;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class LanternMakerRenderer extends BlockEntityRenderer<LanternMakerBlockEntity> {

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(10,ItemStack.EMPTY);
    public LanternMakerRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(LanternMakerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        items = entity.getItems();
        int i = 10;
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up(255));
        for(ItemStack item : items) {
            matrices.push();
            int x = (i%2==0) ? -i : i;
            double offsety = Math.sin((entity.getWorld().getTime()+tickDelta)/x);
            double offsetx = Math.sin((entity.getWorld().getTime() + tickDelta) / i)+0.5;
            double offsetz = Math.cos((entity.getWorld().getTime() + tickDelta) / i) + 0.5;
            matrices.translate( offsetx, 1.25+offsety, offsetz);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) *i));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)* i));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)* i));
            MinecraftClient.getInstance().getItemRenderer().renderItem(item, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers);

            matrices.pop();
            i--;
        }
    }
}
