package net.silentchaos512.funores.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.inventory.ContainerAlloySmelter;

public class GuiAlloySmelter extends GuiContainer {

  private static final ResourceLocation guiTextures = new ResourceLocation(FunOres.MOD_ID,
      "textures/gui/AlloySmelter.png");
  private final InventoryPlayer playerInventory;
  private IInventory tileAlloySmelter;

  public GuiAlloySmelter(InventoryPlayer playerInventory, IInventory smelterInventory) {

    super(new ContainerAlloySmelter(playerInventory, smelterInventory));
    this.playerInventory = playerInventory;
    this.tileAlloySmelter = smelterInventory;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(guiTextures);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    int i1;

    if (TileEntityFurnace.isBurning(this.tileAlloySmelter)) {
      i1 = this.func_175382_i(13);
      this.drawTexturedModalRect(k + 20, l + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
    }

    i1 = this.func_175381_h(24);
    this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
  }

  private int func_175381_h(int p_175381_1_) {

    int j = this.tileAlloySmelter.getField(2);
    int k = this.tileAlloySmelter.getField(3);
    return k != 0 && j != 0 ? j * p_175381_1_ / k : 0;
  }

  private int func_175382_i(int p_175382_1_) {

    int j = this.tileAlloySmelter.getField(1);

    if (j == 0) {
      j = 200;
    }

    return this.tileAlloySmelter.getField(0) * p_175382_1_ / j;
  }
}
