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
        entity.getWorld().getOtherEntities(null,new Box(entity.getPos().getX()-4,entity.getPos().getY()-4,entity.getPos().getZ()-4,entity.getPos().getX()+4,entity.getPos().getY()+4,entity.getPos().getZ()+4)).forEach(entity1->{
           if(entity1 instanceof LivingEntity && !(entity1 instanceof PlayerEntity)) {
               int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos());
               float dx = (float) (entity1.getPos().getX() - 0.5 - entity.getPos().getX());
               float dy = (float) (entity1.getPos().getY()+0.5 - entity.getPos().getY());
               float dz = (float) (entity1.getPos().getZ() - 0.5 - entity.getPos().getZ());
               float distHor = MathHelper.sqrt(dx * dx + dz * dz);
               float dist = MathHelper.sqrt(dx * dx + dz * dz + dy * dy);
               for (float i = 0; i < 1; i += 0.1 + Math.sqrt(dist) / 20) {
                   matrices.push();
                   matrices.translate(MathHelper.lerp(i, 0.5, dx + 0.5), MathHelper.lerp(i, 0, dy + 0.5), MathHelper.lerp(i, 0.5, dz + 0.5));
                   matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float) (-Math.atan2((double) dz, (double) dx) - 1.576)));
                   matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion((float) (-Math.atan2((double) distHor, (double) dy))));
                   MinecraftClient.getInstance().getItemRenderer().renderItem(chain, ModelTransformation.Mode.NONE, lightAbove, overlay, matrices, vertexConsumers);
                   matrices.pop();
               }
           }
        });
    }
}
