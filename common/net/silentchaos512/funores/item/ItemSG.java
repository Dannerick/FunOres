package net.silentchaos512.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.registry.IAddRecipe;
import net.silentchaos512.funores.core.registry.IHasVariants;
import net.silentchaos512.funores.core.util.LocalizationHelper;

public class ItemSG extends Item implements IAddRecipe, IHasVariants {

  protected int subItemCount = 1;
  protected String itemName = "null";
  protected boolean isGlowing = false;
  protected EnumRarity rarity = EnumRarity.COMMON;

  public ItemSG(int subItemCount) {

    setCreativeTab(FunOres.tabFunOres);
    this.subItemCount = subItemCount;
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    int i = 1;
    String s = LocalizationHelper.getItemDescription(itemName, i);
    while (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, i)) && i < 8) {
      list.add(EnumChatFormatting.ITALIC + s);
      s = LocalizationHelper.getItemDescription(itemName, ++i);
    }

    if (i == 1) {
      s = LocalizationHelper.getItemDescription(itemName, 0);
      if (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, 0))) {
        list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getItemDescription(itemName, 0));
      }
    }
  }

  @Override
  public void addOreDict() {

  }
  
  @Override
  public void addRecipes() {

  }

  public String getLocalizedName(ItemStack stack) {

    return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
  }

  @Override
  public EnumRarity getRarity(ItemStack stack) {

    return rarity;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    if (hasSubtypes) {
      for (int i = 0; i < subItemCount; ++i) {
        list.add(new ItemStack(this, 1, i));
      }
    } else {
      list.add(new ItemStack(this, 1, 0));
    }
  }

  public int getSubItemCount() {

    return subItemCount;
  }
  
  @Override
  public String[] getVariantNames() {

    if (hasSubtypes) {
      String[] names = new String[subItemCount];
      for (int i = 0; i < names.length; ++i) {
        names[i] = getFullName() + i;
      }
      return names;
    }
    return new String[] { getFullName() };
  }

  public String getName() {

    return itemName;
  }

  public String getFullName() {

    return FunOres.MOD_ID + ":" + itemName;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    int d = stack.getItemDamage();
    String s = LocalizationHelper.ITEM_PREFIX + itemName;

    if (hasSubtypes) {
      s += d;
    }

    return s;
  }

  public String getUnlocalizedName(String itemName) {

    return LocalizationHelper.ITEM_PREFIX + itemName;
  }

  @Override
  public boolean hasEffect(ItemStack stack) {

    return isGlowing;
  }

  @Override
  public Item setUnlocalizedName(String itemName) {

    this.itemName = itemName;
    return this;
  }
}
