package net.silentchaos512.funores.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.silentchaos512.funores.tile.TileDryingRack;

public class TileDryingRackRender extends TileEntitySpecialRenderer<TileDryingRack> {

  public static Minecraft mc = Minecraft.getMinecraft();
  private TileDryingRack tileDryingRack;

  @Override
  public void renderTileEntityAt(TileDryingRack te, double x, double y, double z,
      float partialTicks, int destroyStage) {

    ItemStack stack = te.getStack();
    tileDryingRack = te;

    // These lines needed?
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    renderItem(te.getWorld(), stack, partialTicks);
    GlStateManager.popMatrix();
  }

  // Mostly copied from Blood Magic...
  private void renderItem(World world, ItemStack stack, float partialTicks) {

    RenderItem itemRenderer = mc.getRenderItem();
    if (stack != null) {
      // GlStateManager.translate(0.5, 0.5, 0.5);
      EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
      entityitem.getEntityItem().stackSize = 1;
      entityitem.hoverStart = 0.0F;
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();

      // float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
      // GlStateManager.rotate(rotation, 0.0F, 1.0F, 0);
      Vec3d vec = getItemPosition();
      GlStateManager.translate(vec.xCoord, vec.yCoord, vec.zCoord);
      GlStateManager.rotate(getItemRotation(), 0f, 1f, 0f);

      float scale = 0.75f;
      GlStateManager.scale(scale, scale, scale);
      GlStateManager.pushAttrib();
      RenderHelper.enableStandardItemLighting();
      itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.popAttrib();

      GlStateManager.enableLighting();
      GlStateManager.popMatrix();
    }
  }

  private Vec3d getItemPosition() {

    final double x = 0.5;
    final double y = 0.5;
    final double z = 0.15;
    switch (tileDryingRack.getMachineState()) {
      case EAST_OFF:
      case EAST_ON:
        return new Vec3d(z, y, x);
      case NORTH_OFF:
      case NORTH_ON:
        return new Vec3d(x, y, 1D - z);
      case SOUTH_OFF:
      case SOUTH_ON:
        return new Vec3d(x, y, z);
      case WEST_OFF:
      case WEST_ON:
        return new Vec3d(1D - z, y, x);
      default:
        return new Vec3d(0, 0, 0);
    }
  }

  private float getItemRotation() {

    switch (tileDryingRack.getMachineState()) {
      case EAST_OFF:
      case EAST_ON:
        return 90f;
      case NORTH_OFF:
      case NORTH_ON:
        return 180f;
      case SOUTH_OFF:
      case SOUTH_ON:
        return 0f;
      case WEST_OFF:
      case WEST_ON:
        return 270f;
      default:
        return 0f;
    }
  }
}
