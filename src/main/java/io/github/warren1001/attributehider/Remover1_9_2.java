package io.github.warren1001.attributehider;

import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EntityVillager;
import net.minecraft.server.v1_9_R2.Item;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.MerchantRecipe;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.NBTTagList;

public class Remover1_9_2 extends Remover {
	
	public Remover1_9_2(Main plugin, Field field) {
		super(plugin, field);
	}

	@Override
	public void remove(Player p) {
		EntityPlayer player = ((CraftPlayer)p).getHandle();
		try {
			for(MerchantRecipe recipe : ((EntityVillager)field.get(player.activeContainer)).getOffers(player)) {
				remove(recipe.getBuyItem1(), recipe.getBuyItem2(), recipe.getBuyItem3());
			}
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void remove(ItemStack... items) {
		for(ItemStack item : items) {
			if(item == null || !plugin.shouldBeModified(Item.getId(item.getItem()))) continue;
			NBTTagCompound tag = item.hasTag() ? item.getTag() : new NBTTagCompound();
			tag.set("AttributeModifiers", new NBTTagList());
			item.setTag(tag);
		}
	}
	
}
