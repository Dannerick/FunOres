package net.silentchaos512.funores.tile;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;
import net.silentchaos512.funores.block.BlockMachine;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumMachineState;

public class TileDryingRack extends TileEntity implements ITickable, IInventory {

  public static final int BASE_DRY_SPEED = 1;

  private ItemStack stack = null;
  private int dryTime = 0;
  private int totalDryTime = 0;
  private float xp = 0;

  public List<String> getDebugLines() {

    String sep = "-----------------------";
    List<String> list = Lists.newArrayList();
    list.add("DEBUG INFO:");
    list.add(sep);
    // variables
    list.add("dryTime = " + dryTime);
    list.add("totalDryTime = " + totalDryTime);
    list.add("drySpeed = " + getDrySpeed());
    list.add("xp = " + xp);
    ItemStack output = getOutput();
    list.add("stack = " + (stack == null ? "null" : stack.getDisplayName()));
    list.add("output = " + (output == null ? "null" : output.getDisplayName()));
    list.add(sep);
    // constants
    return list;
  }

  @Override
  public void update() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    ItemStack output = recipe == null ? null : recipe.getOutput();
    if (recipe != null && output != null) {
      dryTime += getDrySpeed();
      if (dryTime >= totalDryTime) {
        stack = output;
        dryTime = 0;
        xp = recipe.getExperience();
        totalDryTime = getTotalDryTime();
      }
    }
  }

  public boolean interact(EntityPlayer player) {

    ItemStack heldItem = player.getHeldItem();
    if (stack == null && heldItem != null) {
      // Add to rack.
      stack = heldItem.copy();
      stack.stackSize = 1;
      --heldItem.stackSize;
      if (heldItem.stackSize <= 0) {
        heldItem = null;
      }
      dryTime = 0;
      totalDryTime = getTotalDryTime();
      markDirty();
      worldObj.markBlockForUpdate(pos);
    } else if (stack != null) {
      // Remove from rack.
      if (!player.worldObj.isRemote) {
        Vec3 v = new Vec3(player.posX, player.posY + 1.1, player.posZ);
        Vec3 lookVec = player.getLookVec();
        v = v.add(lookVec);
        stack.stackSize = 1; // Not sure why this is necessary...
        EntityItem entityItem = new EntityItem(player.worldObj, v.xCoord, v.yCoord,
            v.zCoord, stack);
//        LogHelper.list(entityItem, entityItem.getEntityItem().stackSize);
        player.worldObj.spawnEntityInWorld(entityItem);
      }
      givePlayerXp(player);
      stack = null;
      dryTime = 0;
      totalDryTime = getTotalDryTime();
      // TODO: Give XP?
      markDirty();
      worldObj.markBlockForUpdate(pos);
    }

    return true;
  }

  protected void givePlayerXp(EntityPlayer player) {

    if (worldObj.isRemote) {
      xp = 0;
      return;
    }

    int amount = (int) xp;
    xp -= amount;
    if (xp > 0.0f) {
      amount = player.worldObj.rand.nextInt((int) (1.0f / xp)) == 0 ? 1 : 0;
    }

    if (amount > 0) {
      player.addExperience(amount);
      player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 0.5F
          * ((player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.7F + 1.8F));
    }

    xp = 0;
  }

  protected int getDrySpeed() {

    boolean canSeeSky = worldObj.canBlockSeeSky(getPos());
    boolean daytime = worldObj.isDaytime(); // Always returns true?
    boolean raining = worldObj.isRaining();

    if (canSeeSky) {
      if (daytime && !raining) {
        return 2 * BASE_DRY_SPEED;
      } else if (raining) {
        return (int) (0.5 * BASE_DRY_SPEED);
      }
    }
    return BASE_DRY_SPEED;
  }

  @Override
  public Packet getDescriptionPacket() {

    NBTTagCompound tags = new NBTTagCompound();
    tags.setInteger("DryTime", dryTime);
    tags.setInteger("TotalDryTime", totalDryTime);
    tags.setFloat("XP", xp);
    if (stack != null) {
      tags.setTag("ItemStack", stack.writeToNBT(new NBTTagCompound()));
    }
    return new S35PacketUpdateTileEntity(pos, 1, tags);
  }

  @Override
  public void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet) {

    NBTTagCompound tags = packet.getNbtCompound();
    dryTime = tags.getInteger("DryTime");
    totalDryTime = tags.getInteger("TotalDryTime");
    xp = tags.getFloat("XP");
    if (tags.hasKey("ItemStack")) {
      stack = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("ItemStack"));
    } else {
      stack = null;
    }

    if (getWorld().isRemote) {
      getWorld().markBlockForUpdate(getPos());
    }
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("ItemStack"));
    dryTime = compound.getShort("DryTime");
    totalDryTime = getTotalDryTime();
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setShort("DryTime", (short) dryTime);
    NBTTagCompound tagCompound = new NBTTagCompound();
    if (stack != null) {
      stack.writeToNBT(tagCompound);
    }
    compound.setTag("ItemStack", tagCompound);
  }

  public ItemStack getOutput() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    if (recipe != null) {
      return recipe.getOutput();
    }
    return null;
  }

  public ItemStack getStack() {

    return stack;
  }

  public void setStack(ItemStack stack) {

    this.stack = stack;
  }

  public EnumMachineState getMachineState() {

    return (EnumMachineState) worldObj.getBlockState(pos).getValue(BlockMachine.FACING);
  }

  public int getTotalDryTime() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    if (recipe != null) {
      return recipe.getDryTime();
    }
    return 0;
  }

  @Override
  public String getName() {

    return "container.funores:dryingrack.name";
  }

  @Override
  public boolean hasCustomName() {

    return false;
  }

  @Override
  public IChatComponent getDisplayName() {

    return null;
  }

  @Override
  public int getSizeInventory() {

    return 1;
  }

  @Override
  public ItemStack getStackInSlot(int index) {

    return stack;
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {

    return removeStackFromSlot(index);
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {

    ItemStack copy = stack;
    stack = null;
    return stack;
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {

    setStack(stack);
  }

  @Override
  public int getInventoryStackLimit() {

    return 1;
  }

  @Override
  public boolean isUseableByPlayer(EntityPlayer player) {

    return this.worldObj.getTileEntity(this.pos) != this ? false
        : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
            (double) this.pos.getZ() + 0.5D) <= 64.0D;
  }

  @Override
  public void openInventory(EntityPlayer player) {

  }

  @Override
  public void closeInventory(EntityPlayer player) {

  }

  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack) {

    return true;
  }

  @Override
  public int getField(int id) {

    switch (id) {
      case 0:
        return dryTime;
      case 1:
        return totalDryTime;
      default:
        return 0;
    }
  }

  @Override
  public void setField(int id, int value) {

    switch (id) {
      case 0:
        dryTime = value;
        break;
      case 1:
        totalDryTime = value;
        break;
    }
  }

  @Override
  public int getFieldCount() {

    return 2;
  }

  @Override
  public void clear() {

    stack = null;
  }
}
