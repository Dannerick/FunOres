package net.silentchaos512.funores.world;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.IHasOre;

public class FunOresGenerator implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

    int dimension = world.provider.getDimensionId();
    int x = 16 * chunkX;
    int z = 16 * chunkZ;

    switch (dimension) {
      case -1:
        generateNether(world, random, x, z);
        break;
      case 0:
        generateSurface(world, random, x, z);
        break;
      case 1:
        generateEnd(world, random, x, z);
        break;
      default:
        generateSurface(world, random, x, z);;
    }
  }

  private void generateSurface(World world, Random random, int posX, int posZ) {

    final int dim = 0;
    for (EnumMetal metal : EnumMetal.values()) {
      if (metal.dimension == dim) {
        generateOre(metal.getConfig(), world, random, posX, posZ);
      }
    }
    for (EnumMeat meat : EnumMeat.values()) {
      if (meat.dimension == dim) {
        generateOre(meat.getConfig(), world, random, posX, posZ);
      }
    }
    for (EnumMob mob : EnumMob.values()) {
      if (mob.dimension == dim) {
        generateOre(mob.getConfig(), world, random, posX, posZ);
      }
    }
  }

  private void generateNether(World world, Random random, int posX, int posZ) {

    Predicate predicate = BlockHelper.forBlock(Blocks.netherrack);

    final int dim = -1;
    for (EnumMetal metal : EnumMetal.values()) {
      if (metal.dimension == dim) {
        generateOre(metal.getConfig(), world, random, posX, posZ, predicate);
      }
    }
    for (EnumMeat meat : EnumMeat.values()) {
      if (meat.dimension == dim) {
        generateOre(meat.getConfig(), world, random, posX, posZ, predicate);
      }
    }
    for (EnumMob mob : EnumMob.values()) {
      if (mob.dimension == dim) {
        generateOre(mob.getConfig(), world, random, posX, posZ, predicate);
      }
    }
  }

  private void generateEnd(World world, Random random, int posX, int posZ) {

    // Nothing
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ) {

    generateOre(ore, world, random, posX, posZ, null);
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ,
      Predicate predicate) {
    
    if (!ore.enabled) {
      return;
    }

    if (!(ore.ore instanceof IHasOre)) {
      LogHelper.debug(ore.oreName + " is not an ore?");
      return;
    }

    for (int i = 0; i < ore.clusterCount; ++i) {
      if (random.nextInt(ore.rarity) == 0) {
        int x = posX + random.nextInt(16);
        int y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
        int z = posZ + random.nextInt(16);

        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = ((IHasOre) ore.ore).getOre();

        if (predicate == null) {
          new WorldGenMinable(state, ore.clusterSize).generate(world, random, pos);
        } else {
          new WorldGenMinable(state, ore.clusterSize, predicate).generate(world, random, pos);
        }
      }
    }
  }
}