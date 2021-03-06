
package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

@AutoReg.ModElement.Tag
public class BlockHiveCreepTop extends AutoReg.ModElement {
	@GameRegistry.ObjectHolder("wyrmsofnyrus:hivecreeptop")
	public static final Block block = null;
	public BlockHiveCreepTop(AutoReg instance) {
		super(instance, 3);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("hivecreeptop"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("wyrmsofnyrus:hivecreeptop", "inventory"));
	}
	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(BlockMaterials.CREEP);
			setUnlocalizedName("hivecreeptop");
			setSoundType(SoundType.SLIME);
			setHarvestLevel("shovel", 1);
			setHardness(2F);
			setResistance(5F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabWyrms.tab);
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Spreads!");
		}

		@Override
		public int tickRate(World world) {
			return Invasion.creepTickRate;
		}

		@Override
		public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
			drops.add(new ItemStack(BlockHiveCreepBlock.block, (int) (1)));
		}

		@Override
		public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
			super.onBlockAdded(world, pos, state);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
		}

		@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
			super.updateTick(world, pos, state, random);
			HiveCreepSpreadFurther.executescript(pos, world);
			ActiveCreepBlock.CreepSpread(pos, world, BlockHiveCreepTopInactive.block.getDefaultState());
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}
	}
}
