package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaExtended;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.LogHelper;
import scala.reflect.internal.Mode;

public class ItemCraftingItem extends ItemSL implements IDisableable {

  public static final int BASE_METALS_COUNT = 18;

  public final String craftingItemName;
  public final boolean isAlloy;
  public final boolean isGear;
  public final boolean isPlate;

  public ItemCraftingItem(String name, boolean isAlloy) {

    super(
        isAlloy ? EnumAlloy.values().length
            : EnumMetal.values().length + EnumVanillaMetal.values().length,
        FunOres.MOD_ID, Names.CRAFTING_ITEM);
    this.craftingItemName = name;
    this.isAlloy = isAlloy;
    this.isGear = craftingItemName.equals(Names.GEAR);
    this.isPlate = craftingItemName.equals(Names.PLATE);
  }

  public List<IMetal> getMetals() {

    if (isAlloy) {
      // Alloys
      return new ArrayList<IMetal>(Arrays.asList(EnumAlloy.values()));
    } else {
      // Basic metals (including vanilla)
      List<IMetal> metals = new ArrayList<IMetal>(Arrays.asList(EnumMetal.values()));
      metals.addAll(Arrays.asList(EnumVanillaMetal.values()));
      // Extended vanilla
      for (EnumVanillaExtended extended : EnumVanillaExtended.values())
        if ((isGear && extended.allowGear) || (isPlate && extended.allowPlate))
          metals.add(extended);
      return metals;
    }
  }

  @Override
  public void addRecipes() {

    if (isGear) {
      for (IMetal metal : getMetals()) {
        ItemStack gear = metal.getGear();
        if (gear != null && !FunOres.registry.isItemDisabled(gear)) {
          String mat = metal instanceof EnumVanillaExtended
              ? ((EnumVanillaExtended) metal).getMaterialOreDictKey()
              : "ingot" + metal.getMetalName();
          GameRegistry.addRecipe(new ShapedOreRecipe(gear, " m ", "mim", " m ", 'm', mat, 'i',
              metal == EnumVanillaExtended.WOOD || metal == EnumVanillaExtended.STONE ? "stickWood"
                  : "ingotIron"));
        }
      }
    }
  }

  @Override
  public void addOreDict() {

    ItemStack stack;

    if (isGear) {
      for (IMetal metal : getMetals()) {
        stack = new ItemStack(this, 1, metal.getMeta());
        if (!FunOres.registry.isItemDisabled(stack)) {
          OreDictionary.registerOre("gear" + metal.getMetalName(), stack);
          if (metal == EnumMetal.ALUMINIUM) {
            OreDictionary.registerOre("gearAluminum", stack);
          }
        }
      }
    } else if (isPlate) {
      for (IMetal metal : getMetals()) {
        stack = new ItemStack(this, 1, metal.getMeta());
        if (!FunOres.registry.isItemDisabled(stack)) {
          OreDictionary.registerOre("plate" + metal.getMetalName(), stack);
          if (metal == EnumMetal.ALUMINIUM) {
            OreDictionary.registerOre("plateAluminum", stack);
          }
        }
      }
    } else {
      FunOres.logHelper.warning("CraftingItem.addOreDict - Unknown item type: " + craftingItemName);
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    String prefix = FunOres.MOD_ID + ":" + craftingItemName;
    ModelResourceLocation[] models = new ModelResourceLocation[32];
    for (IMetal metal : getMetals()) {
      models[metal.getMeta()] = new ModelResourceLocation(prefix + metal.getMetalName());
    }
    return Arrays.asList(models);
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    list.addAll(getSubItems(item));
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    List<ItemStack> ret = Lists.newArrayList();
    for (IMetal metal : getMetals())
      ret.add(new ItemStack(item, 1, metal.getMeta()));
    return ret;
  }

  @Override
  public String getNameForStack(ItemStack stack) {

    String metalName = null;
    int meta = stack.getItemDamage();
    List<IMetal> metals = getMetals();
    if (meta >= 0 && meta < metals.size() && metals.get(meta) != null) {
      metalName = metals.get(meta).getMetalName();
    } else {
      for (IMetal metal : Lists.reverse(metals)) {
        if (metal.getMeta() == meta) {
          metalName = metal.getMetalName();
        }
      }
    }
    return craftingItemName + metalName;
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    // TODO
  }
}