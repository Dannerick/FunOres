package net.silentchaos512.funores.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockSG;
import net.silentchaos512.funores.core.util.LocalizationHelper;

public class ItemBlockSG extends ItemBlock {

    protected Block block;
    protected String itemName = "null";
    
    public ItemBlockSG(Block block) {

        super(block);
        this.setMaxDamage(0);
        
        // Block and block name
        this.block = block;
        this.itemName = block.getUnlocalizedName().substring(5);
        
        // Subtypes?
        if (block instanceof BlockSG) {
            BlockSG blockSG = (BlockSG) block;
            this.setHasSubtypes(blockSG.getHasSubtypes());
        }
    }
    
    @Override
    public int getMetadata(int meta) {
      
      return meta;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        
        int i = 1;
        String s = LocalizationHelper.getBlockDescription(itemName, i);
        while (!s.equals(LocalizationHelper.getBlockDescriptionKey(itemName, i)) && i < 8) {
            list.add(EnumChatFormatting.ITALIC + s);
            s = LocalizationHelper.getBlockDescription(itemName, ++i);
        }
        
        if (i == 1) {
            s = LocalizationHelper.getBlockDescription(itemName, 0);
            if (!s.equals(LocalizationHelper.getBlockDescriptionKey(itemName, 0))) {
                list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getBlockDescription(itemName, 0));
            }
        }
    }
    
//    @Override
//    public IIcon getIconFromDamage(int meta) {
//        
//        if (hasSubtypes && block instanceof BlockSG) {
//            BlockSG b = (BlockSG) block;
//            if (b.icons != null && meta < b.icons.length) {
//                return b.icons[meta];
//            }
//        }
//        return super.getIconFromDamage(meta);
//    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        
        String s = "tile.";
        s += FunOres.RESOURCE_PREFIX;
        s += itemName;
        
        if (hasSubtypes) {
            s += stack.getItemDamage();
        }

        return s;
    }
}
