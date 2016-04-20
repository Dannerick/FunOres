package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.block.BlockSL;

public class AlloyBlock extends BlockSL {
  
  public static final PropertyEnum ALLOY = PropertyEnum.create("alloy", EnumAlloy.class);

  public AlloyBlock() {

    super(EnumAlloy.count(), FunOres.MOD_ID, Names.ALLOY_BLOCK, Material.IRON);

    setHardness(3.0f);
    setResistance(30.0f);
    setSoundType(SoundType.METAL);
    setHarvestLevel("pickaxe", 1);

//    setHasSubtypes(true);
    setUnlocalizedName(Names.ALLOY_BLOCK);
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy alloy : EnumAlloy.values()) {
      OreDictionary.registerOre("block" + alloy.getMetalName(), alloy.getBlock());
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {
    
    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumAlloy metal : EnumAlloy.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Block" + metal.getMetalName()));
    }
    return models;
  }
  
  @Override
  public int damageDropped(IBlockState state) {
    
    return ((EnumAlloy) state.getValue(ALLOY)).getMeta();
  }
  
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumAlloy.count(); ++i) {
      list.add(new ItemStack(item, 1, EnumAlloy.values()[i].getMeta()));
    }
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta) {
    
    return this.getDefaultState().withProperty(ALLOY, EnumAlloy.byMetadata(meta));
  }
  
  @Override
  public int getMetaFromState(IBlockState state) {
    
    return ((EnumAlloy) state.getValue(ALLOY)).getMeta();
  }
  
  @Override
  protected BlockStateContainer createBlockState() {
    
    return new BlockStateContainer(this, new IProperty[] { ALLOY });
  }
}
