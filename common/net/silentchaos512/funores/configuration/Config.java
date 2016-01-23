package net.silentchaos512.funores.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.EnumVanillaOre;
import net.silentchaos512.funores.lib.ExtraRecipes;

public class Config {

  // Misc
  public static boolean machinesCanBurn = true;

  // Metal ores
  public static ConfigOptionOreGen copper = new ConfigOptionOreGen(EnumMetal.COPPER);
  public static ConfigOptionOreGen tin = new ConfigOptionOreGen(EnumMetal.TIN);
  public static ConfigOptionOreGen silver = new ConfigOptionOreGen(EnumMetal.SILVER);
  public static ConfigOptionOreGen lead = new ConfigOptionOreGen(EnumMetal.LEAD);
  public static ConfigOptionOreGen nickel = new ConfigOptionOreGen(EnumMetal.NICKEL);
  public static ConfigOptionOreGen platinum = new ConfigOptionOreGen(EnumMetal.PLATINUM);
  public static ConfigOptionOreGen aluminium = new ConfigOptionOreGen(EnumMetal.ALUMINIUM);
  public static ConfigOptionOreGen zinc = new ConfigOptionOreGen(EnumMetal.ZINC);
  public static ConfigOptionOreGen titanium = new ConfigOptionOreGen(EnumMetal.TITANIUM);

  // Meat ores
  public static ConfigOptionOreGenBonus pig = new ConfigOptionOreGenBonus(EnumMeat.PIG);
  public static ConfigOptionOreGenBonus fish = new ConfigOptionOreGenBonus(EnumMeat.FISH);
  public static ConfigOptionOreGenBonus cow = new ConfigOptionOreGenBonus(EnumMeat.COW);
  public static ConfigOptionOreGenBonus chicken = new ConfigOptionOreGenBonus(EnumMeat.CHICKEN);
  public static ConfigOptionOreGenBonus rabbit = new ConfigOptionOreGenBonus(EnumMeat.RABBIT);
  public static ConfigOptionOreGenBonus sheep = new ConfigOptionOreGenBonus(EnumMeat.SHEEP);
  public static ConfigOptionOreGenBonus squid = new ConfigOptionOreGenBonus(EnumMeat.SQUID);

  // Mob ores
  public static ConfigOptionOreGenBonus zombie = new ConfigOptionOreGenBonus(EnumMob.ZOMBIE);
  public static ConfigOptionOreGenBonus skeleton = new ConfigOptionOreGenBonus(EnumMob.SKELETON);
  public static ConfigOptionOreGenBonus creeper = new ConfigOptionOreGenBonus(EnumMob.CREEPER);
  public static ConfigOptionOreGenBonus spider = new ConfigOptionOreGenBonus(EnumMob.SPIDER);
  public static ConfigOptionOreGenBonus enderman = new ConfigOptionOreGenBonus(EnumMob.ENDERMAN);
  public static ConfigOptionOreGenBonus slime = new ConfigOptionOreGenBonus(EnumMob.SLIME);
  public static ConfigOptionOreGenBonus witch = new ConfigOptionOreGenBonus(EnumMob.WITCH);
  public static ConfigOptionOreGenBonus pigman = new ConfigOptionOreGenBonus(EnumMob.PIGMAN);
  public static ConfigOptionOreGenBonus ghast = new ConfigOptionOreGenBonus(EnumMob.GHAST);
  public static ConfigOptionOreGenBonus magmaCube = new ConfigOptionOreGenBonus(EnumMob.MAGMA_CUBE);
  public static ConfigOptionOreGenBonus wither = new ConfigOptionOreGenBonus(EnumMob.WITHER);
  public static ConfigOptionOreGenBonus blaze = new ConfigOptionOreGenBonus(EnumMob.BLAZE);

  // Vanilla ores
  public static ConfigOptionOreGenReplace iron = new ConfigOptionOreGenReplace(EnumVanillaOre.IRON);
  public static ConfigOptionOreGenReplace gold = new ConfigOptionOreGenReplace(EnumVanillaOre.GOLD);
  public static ConfigOptionOreGenReplace diamond = new ConfigOptionOreGenReplace(
      EnumVanillaOre.DIAMOND);
  public static ConfigOptionOreGenReplace emerald = new ConfigOptionOreGenReplace(
      EnumVanillaOre.EMERALD);
  public static ConfigOptionOreGenReplace coal = new ConfigOptionOreGenReplace(EnumVanillaOre.COAL);
  public static ConfigOptionOreGenReplace redstone = new ConfigOptionOreGenReplace(
      EnumVanillaOre.REDSTONE);
  public static ConfigOptionOreGenReplace lapis = new ConfigOptionOreGenReplace(
      EnumVanillaOre.LAPIS);
  public static ConfigOptionOreGenReplace quartz = new ConfigOptionOreGenReplace(
      EnumVanillaOre.QUARTZ);

  private static Configuration c;

  public static final String CATEGORY_METAL_ORE = "MetalOre";
  public static final String CATEGORY_MEAT_ORE = "MeatOre";
  public static final String CATEGORY_MOB_ORE = "MobOre";
  public static final String CATEGORY_VANILLA_ORE = "vanilla_ore";
  public static final String CATEGORY_RECIPE = "Recipe";
  public static final String CATEGORY_MISC = "Misc";
  public static final String CATEGORY_RECIPE_ALLOY_SMELTER = "recipe_alloy_smelter";

  public static final String COMMENT_EXAMPLE = "An example ore with comments on each individual setting.";
  public static final String COMMENT_METAL_ORE = "The metal ores like copper and titanium.";
  public static final String COMMENT_MEAT_ORE = "The meat (passive mob) ores.";
  public static final String COMMENT_MOB_ORE = "The mob (hostile mob) ores.";
  public static final String COMMENT_VANILLA_ORE = "Fun Ores can optionally add to or replace vanilla ores. By default, these are all disabled. Defaults if\n"
      + "enabled are set to add to vanilla generation, rather than replace it.";
  public static final String COMMENT_RECIPE_ALLOY_SMELTER = "You can disable alloy smelter recipes here. Set to false to disable the recipe.";

  public static void init(File file) {

    c = new Configuration(file);

    try {
      c.load();
      ExtraRecipes.config = c;

      /*
       * Misc configs
       */

      machinesCanBurn = c.getBoolean("MachinesCanBurn", CATEGORY_MISC, machinesCanBurn,
          "If true, active machines can damage entities that step on top of them. Ouch.");

      /*
       * Example Ore
       */

      c.setCategoryComment(ConfigOptionOreGen.CATEGORY_EXAMPLE, COMMENT_EXAMPLE);

      (new ConfigOptionOreGenBonus(true)).loadValue(c, "");

      /*
       * Metal Ores
       */

      c.setCategoryComment(CATEGORY_METAL_ORE, COMMENT_METAL_ORE);

      copper.enabled = true;
      copper.clusterCount = 10;
      copper.clusterSize = 8;
      copper.minY = 40;
      copper.maxY = 75;
      copper.rarity = 1;
      copper.loadValue(c, CATEGORY_METAL_ORE);

      tin.enabled = true;
      tin.clusterCount = 10;
      tin.clusterSize = 8;
      tin.minY = 20;
      tin.maxY = 55;
      tin.rarity = 1;
      tin.loadValue(c, CATEGORY_METAL_ORE);

      silver.enabled = true;
      silver.clusterCount = 6;
      silver.clusterSize = 8;
      silver.minY = 5;
      silver.maxY = 30;
      silver.rarity = 1;
      silver.loadValue(c, CATEGORY_METAL_ORE);

      lead.enabled = true;
      lead.clusterCount = 4;
      lead.clusterSize = 8;
      lead.minY = 10;
      lead.maxY = 35;
      lead.rarity = 1;
      lead.loadValue(c, CATEGORY_METAL_ORE);

      nickel.enabled = true;
      nickel.clusterCount = 2;
      nickel.clusterSize = 4;
      nickel.minY = 5;
      nickel.maxY = 20;
      nickel.rarity = 1;
      nickel.loadValue(c, CATEGORY_METAL_ORE);

      platinum.enabled = true;
      platinum.clusterCount = 4;
      platinum.clusterSize = 6;
      platinum.minY = 5;
      platinum.maxY = 15;
      platinum.rarity = 10;
      platinum.loadValue(c, CATEGORY_METAL_ORE);

      aluminium.enabled = true;
      aluminium.clusterCount = 6;
      aluminium.clusterSize = 7;
      aluminium.minY = 10;
      aluminium.maxY = 50;
      aluminium.rarity = 1;
      aluminium.loadValue(c, CATEGORY_METAL_ORE);

      zinc.enabled = true;
      zinc.clusterCount = 4;
      zinc.clusterSize = 8;
      zinc.minY = 20;
      zinc.maxY = 70;
      zinc.rarity = 1;
      zinc.loadValue(c, CATEGORY_METAL_ORE);

      titanium.enabled = true;
      titanium.clusterCount = 3;
      titanium.clusterSize = 6;
      titanium.minY = 5;
      titanium.maxY = 20;
      titanium.rarity = 10;
      titanium.loadValue(c, CATEGORY_METAL_ORE);

      /*
       * Meat Ores
       */

      c.setCategoryComment(CATEGORY_MEAT_ORE, COMMENT_MEAT_ORE);

      int meatClusterCount = 1;
      int meatClusterSize = 15;
      int meatMinY = 32;
      int meatMaxY = 96;
      int meatRarity = 14;

      pig.enabled = true;
      pig.clusterCount = meatClusterCount;
      pig.clusterSize = meatClusterSize;
      pig.minY = meatMinY;
      pig.maxY = meatMaxY;
      pig.rarity = meatRarity;
      pig.addDrop(ConfigItemDrop.getKey("minecraft:porkchop", 1, 0, 1.0f, 0.0f, 1.0f));
      pig.addDrop(ConfigItemDrop.getKey("minecraft:saddle", 1, 0, 0.025f, 0.01f, 0.0f));
      pig.loadValue(c, CATEGORY_MEAT_ORE);

      fish.enabled = true;
      fish.clusterCount = meatClusterCount;
      fish.clusterSize = meatClusterSize;
      fish.minY = meatMinY;
      fish.maxY = meatMaxY;
      fish.rarity = meatRarity;
      fish.addDrop(ConfigItemDrop.getKey("minecraft:fish", 1, 0, 1.0f, 0.0f, 1.0f));
      fish.addDrop(ConfigItemDrop.getKey("minecraft:fish", 1, 1, 0.3f, 0.0f, 0.5f));
      fish.addDrop(ConfigItemDrop.getKey("minecraft:fish", 1, 2, 0.1f, 0.0f, 0.5f));
      fish.addDrop(ConfigItemDrop.getKey("minecraft:fish", 1, 3, 0.1f, 0.0f, 0.5f));
      fish.loadValue(c, CATEGORY_MEAT_ORE);

      cow.enabled = true;
      cow.clusterCount = meatClusterCount;
      cow.clusterSize = meatClusterSize;
      cow.minY = meatMinY;
      cow.maxY = meatMaxY;
      cow.rarity = meatRarity;
      cow.addDrop(ConfigItemDrop.getKey("minecraft:beef", 1, 0, 1.0f, 0.0f, 1.0f));
      cow.addDrop(ConfigItemDrop.getKey("minecraft:leather", 1, 0, 0.75f, 0.03f, 1.0f));
      cow.loadValue(c, CATEGORY_MEAT_ORE);

      chicken.enabled = true;
      chicken.clusterCount = meatClusterCount;
      chicken.clusterSize = meatClusterSize;
      chicken.minY = meatMinY;
      chicken.maxY = meatMaxY;
      chicken.rarity = meatRarity;
      chicken.addDrop(ConfigItemDrop.getKey("minecraft:chicken", 1, 0, 1.0f, 0.0f, 1.0f));
      chicken.addDrop(ConfigItemDrop.getKey("minecraft:feather", 2, 0, 0.36f, 0.04f, 1.0f));
      chicken.addDrop(ConfigItemDrop.getKey("minecraft:egg", 2, 0, 0.15f, 0.02f, 0.7f));
      chicken.loadValue(c, CATEGORY_MEAT_ORE);

      rabbit.enabled = true;
      rabbit.clusterCount = meatClusterCount;
      rabbit.clusterSize = meatClusterSize;
      rabbit.minY = meatMinY;
      rabbit.maxY = meatMaxY;
      rabbit.rarity = meatRarity;
      rabbit.addDrop(ConfigItemDrop.getKey("minecraft:rabbit", 1, 0, 1.0f, 0.0f, 1.0f));
      rabbit.addDrop(ConfigItemDrop.getKey("minecraft:rabbit_hide", 1, 0, 0.6f, 0.05f, 1.0f));
      rabbit.addDrop(ConfigItemDrop.getKey("minecraft:rabbit_foot", 1, 0, 0.07f, 0.01f, 0.5f));
      rabbit.loadValue(c, CATEGORY_MEAT_ORE);

      sheep.enabled = true;
      sheep.clusterCount = meatClusterCount;
      sheep.clusterSize = meatClusterSize;
      sheep.minY = meatMinY;
      sheep.maxY = meatMaxY;
      sheep.rarity = meatRarity;
      sheep.addDrop(ConfigItemDrop.getKey("minecraft:mutton", 1, 0, 1.0f, 0.0f, 1.0f));
      sheep.addDrop(ConfigItemDrop.getKey("minecraft:wool", 1, 0, 0.5f, 0.05f, 0.7f));
      sheep.loadValue(c, CATEGORY_MEAT_ORE);

      squid.enabled = true;
      squid.clusterCount = meatClusterCount;
      squid.clusterSize = meatClusterSize;
      squid.minY = meatMinY;
      squid.maxY = meatMaxY;
      squid.rarity = meatRarity;
      squid.addDrop(ConfigItemDrop.getKey("minecraft:dye", 1, 0, 1.0f, 0.0f, 1.0f));
      squid.loadValue(c, CATEGORY_MEAT_ORE);

      /*
       * Mob Ores
       */

      c.setCategoryComment(CATEGORY_MOB_ORE, COMMENT_MOB_ORE);

      int mobClusterCount = 1;
      int mobClusterSize = 17;
      int mobMinY = 24;
      int mobMaxY = 86;
      int mobRarity = 20;

      // Overworld

      zombie.enabled = true;
      zombie.clusterCount = mobClusterCount;
      zombie.clusterSize = mobClusterSize;
      zombie.minY = mobMinY;
      zombie.maxY = mobMaxY;
      zombie.rarity = mobRarity;
      zombie.addDrop(ConfigItemDrop.getKey("minecraft:rotten_flesh", 1, 0, 1.0f, 0.0f, 1.0f));
      zombie.addDrop(ConfigItemDrop.getKey("minecraft:skull", 1, 2, 0.03f, 0.015f, 0.0f));
      zombie.addDrop(ConfigItemDrop.getKey("minecraft:iron_ingot", 1, 0, 0.01f, 0.005f, 0.0f));
      zombie.addDrop(ConfigItemDrop.getKey("minecraft:carrot", 1, 0, 0.01f, 0.005f, 0.0f));
      zombie.addDrop(ConfigItemDrop.getKey("minecraft:potato", 1, 0, 0.01f, 0.005f, 0.0f));
      zombie.loadValue(c, CATEGORY_MOB_ORE);

      skeleton.enabled = true;
      skeleton.clusterCount = mobClusterCount;
      skeleton.clusterSize = mobClusterSize;
      skeleton.minY = mobMinY;
      skeleton.maxY = mobMaxY;
      skeleton.rarity = mobRarity;
      skeleton.addDrop(ConfigItemDrop.getKey("minecraft:bone", 1, 0, 1.0f, 0.0f, 1.0f));
      skeleton.addDrop(ConfigItemDrop.getKey("minecraft:arrow", 1, 0, 0.75f, 0.0f, 0.7f));
      skeleton.addDrop(ConfigItemDrop.getKey("minecraft:skull", 1, 0, 0.03f, 0.015f, 0.0f));
      skeleton.loadValue(c, CATEGORY_MOB_ORE);

      creeper.enabled = true;
      creeper.clusterCount = mobClusterCount;
      creeper.clusterSize = mobClusterSize;
      creeper.minY = mobMinY;
      creeper.maxY = mobMaxY;
      creeper.rarity = mobRarity;
      creeper.addDrop(ConfigItemDrop.getKey("minecraft:gunpowder", 1, 0, 1.0f, 0.0f, 1.0f));
      creeper.addDrop(ConfigItemDrop.getKey("minecraft:skull", 1, 4, 0.03f, 0.015f, 0.0f));
      creeper.loadValue(c, CATEGORY_MOB_ORE);

      spider.enabled = true;
      spider.clusterCount = mobClusterCount;
      spider.clusterSize = mobClusterSize;
      spider.minY = mobMinY;
      spider.maxY = mobMaxY;
      spider.rarity = mobRarity;
      spider.addDrop(ConfigItemDrop.getKey("minecraft:string", 1, 0, 1.0f, 0.0f, 1.0f));
      spider.addDrop(ConfigItemDrop.getKey("minecraft:spider_eye", 1, 0, 0.5f, 0.0f, 0.7f));
      spider.loadValue(c, CATEGORY_MOB_ORE);

      enderman.enabled = true;
      enderman.clusterCount = mobClusterCount;
      enderman.clusterSize = mobClusterSize;
      enderman.minY = mobMinY;
      enderman.maxY = mobMaxY;
      enderman.rarity = mobRarity;
      enderman.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 0, 1.0f, 0.0f, 0.7f));
      enderman.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 0, 0.10f, 0.0f, 0.4f));
      enderman.loadValue(c, CATEGORY_MOB_ORE);

      slime.enabled = true;
      slime.clusterCount = mobClusterCount;
      slime.clusterSize = mobClusterSize;
      slime.minY = mobMinY;
      slime.maxY = mobMaxY;
      slime.rarity = mobRarity;
      slime.addDrop(ConfigItemDrop.getKey("minecraft:slime_ball", 1, 0, 1.0f, 0.0f, 1.5f));
      slime.addDrop(ConfigItemDrop.getKey("minecraft:slime_ball", 1, 0, 0.5f, 0.05f, 0.5f));
      slime.loadValue(c, CATEGORY_MOB_ORE);

      witch.enabled = true;
      witch.clusterCount = mobClusterCount;
      witch.clusterSize = mobClusterSize;
      witch.minY = mobMinY;
      witch.maxY = mobMaxY;
      witch.rarity = mobRarity;
      witch.addDrop(ConfigItemDrop.getKey("minecraft:glass_bottle", 2, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:glowstone_dust", 1, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:gunpowder", 1, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:redstone", 1, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:spider_eye", 1, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:stick", 2, 0, 1.0f, 0.0f, 1.0f));
      witch.addDrop(ConfigItemDrop.getKey("minecraft:sugar", 2, 0, 1.0f, 0.0f, 1.0f));
      witch.pick = 2;
      witch.loadValue(c, CATEGORY_MOB_ORE);

      // Nether

      mobClusterCount = 2;
      mobClusterSize = 17;
      mobMinY = 48;
      mobMaxY = 116;
      mobRarity = 24;

      pigman.enabled = true;
      pigman.clusterCount = mobClusterCount;
      pigman.clusterSize = mobClusterSize;
      pigman.minY = mobMinY;
      pigman.maxY = mobMaxY;
      pigman.rarity = mobRarity;
      pigman.addDrop(ConfigItemDrop.getKey("minecraft:rotten_flesh", 1, 0, 1.0f, 0.0f, 1.0f));
      pigman.addDrop(ConfigItemDrop.getKey("minecraft:gold_nugget", 1, 0, 0.50f, 0.06f, 0.7f));
      pigman.addDrop(ConfigItemDrop.getKey("minecraft:gold_ingot", 1, 0, 0.025f, 0.01f, 0.0f));
      pigman.loadValue(c, CATEGORY_MOB_ORE);

      ghast.enabled = true;
      ghast.clusterCount = mobClusterCount;
      ghast.clusterSize = mobClusterSize;
      ghast.minY = mobMinY;
      ghast.maxY = mobMaxY;
      ghast.rarity = mobRarity;
      ghast.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 2, 1.0f, 0.00f, 0.7f));
      ghast.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 2, 0.25f, 0.0f, 0.4f));
      ghast.addDrop(ConfigItemDrop.getKey("minecraft:gunpowder", 1, 0, 1.0f, 0.0f, 1.0f));
      ghast.loadValue(c, CATEGORY_MOB_ORE);

      magmaCube.enabled = true;
      magmaCube.clusterCount = mobClusterCount;
      magmaCube.clusterSize = mobClusterSize;
      magmaCube.minY = mobMinY;
      magmaCube.maxY = mobMaxY;
      magmaCube.rarity = mobRarity;
      magmaCube.addDrop(ConfigItemDrop.getKey("minecraft:magma_cream", 1, 0, 1.0f, 0.0f, 1.0f));
      magmaCube.loadValue(c, CATEGORY_MOB_ORE);

      wither.enabled = true;
      wither.clusterCount = mobClusterCount;
      wither.clusterSize = mobClusterSize;
      wither.minY = mobMinY;
      wither.maxY = mobMaxY;
      wither.rarity = mobRarity;
      wither.addDrop(ConfigItemDrop.getKey("minecraft:bone", 1, 0, 1.0f, 0.0f, 1.0f));
      wither.addDrop(ConfigItemDrop.getKey("minecraft:coal", 1, 0, 0.6f, 0.05f, 1.0f));
      wither.addDrop(ConfigItemDrop.getKey("minecraft:skull", 1, 1, 0.03f, 0.01f, 0.0f));
      wither.loadValue(c, CATEGORY_MOB_ORE);

      blaze.enabled = true;
      blaze.clusterCount = mobClusterCount;
      blaze.clusterSize = mobClusterSize;
      blaze.minY = mobMinY;
      blaze.maxY = mobMaxY;
      blaze.rarity = mobRarity;
      blaze.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 1, 1.0f, 0.0f, 0.7f));
      blaze.addDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 1, 0.10f, 0.0f, 0.4f));
      blaze.loadValue(c, CATEGORY_MOB_ORE);

      // Vanilla Ores
      // All are disabled by default. Default settings are set so that bonus ores will spawn if
      // enabled, not replacements.

      c.setCategoryComment(CATEGORY_VANILLA_ORE, COMMENT_VANILLA_ORE);

      iron.enabled = false;
      iron.clusterCount = 4;
      iron.maxY = 64;
      iron.loadValue(c, CATEGORY_VANILLA_ORE);

      gold.enabled = false;
      gold.clusterCount = 4;
      gold.maxY = 32;
      gold.loadValue(c, CATEGORY_VANILLA_ORE);

      diamond.enabled = false;
      diamond.clusterCount = 1;
      diamond.maxY = 16;
      diamond.loadValue(c, CATEGORY_VANILLA_ORE);

      emerald.enabled = false;
      emerald.clusterCount = 2;
      emerald.clusterSize = 1;
      emerald.minY = 8;
      emerald.maxY = 32;
      emerald.rarity = 24;
      emerald.loadValue(c, CATEGORY_VANILLA_ORE);

      coal.enabled = false;
      coal.clusterCount = 0;
      coal.clusterSize = 16;
      coal.loadValue(c, CATEGORY_VANILLA_ORE);

      redstone.enabled = false;
      redstone.clusterCount = 2;
      redstone.maxY = 16;
      redstone.loadValue(c, CATEGORY_VANILLA_ORE);

      lapis.enabled = false;
      lapis.clusterCount = 0;
      lapis.maxY = 16;
      lapis.loadValue(c, CATEGORY_VANILLA_ORE);

      quartz.enabled = false;
      quartz.clusterCount = 0;
      quartz.clusterSize = 13;
      quartz.loadValue(c, CATEGORY_VANILLA_ORE);

    } catch (Exception e) {
      LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
      LogHelper.severe(e);
    }
  }

  public static void save() {

    if (c.hasChanged()) {
      c.save();
    }
  }

  public static Configuration getConfiguration() {

    return c;
  }
}
