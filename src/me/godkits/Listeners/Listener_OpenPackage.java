package me.godkits.Listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;


public class Listener_OpenPackage implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		GodKitsApi api = new GodKitsApi();
		ItemStack item = p.getItemInHand();
		
		if(p.getItemInHand() != null) {
		if(p.getItemInHand().getType() == Material.CHEST && item.getItemMeta().getDisplayName() != null) {
		if(api.getKitFromDisplayname(Chat.format(p.getItemInHand().getItemMeta().getDisplayName())) != null) {
			String kit = api.getKitFromDisplayname(Chat.format(p.getItemInHand().getItemMeta().getDisplayName()));
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack kitpackage = new ItemStack(p.getItemInHand().getType(), 1);
			kitpackage.setItemMeta(p.getItemInHand().getItemMeta());
			
			p.getInventory().removeItem(kitpackage);
			Kits.sendKit(p, kit);
			e.setCancelled(true);
			p.sendMessage(Messages.getMessage("OPENED_PACKAGE").replace("%kit%", item.getItemMeta().getDisplayName()));
		} else if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			showKit(p, kit);
			e.setCancelled(true);
		}
		}
		}
		}
	}
	
	public void showKit(Player p, String kit) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		Inventory inv = Bukkit.createInventory(null, 54,
				Chat.format(plugin.getConfig().getString("GUI.kit-preview.gui-name")));

		ItemStack glaspane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		inv.setItem(36, glaspane);
		inv.setItem(37, glaspane);
		inv.setItem(38, glaspane);
		inv.setItem(39, glaspane);
		inv.setItem(40, glaspane);
		inv.setItem(41, glaspane);
		inv.setItem(42, glaspane);
		inv.setItem(43, glaspane);
		inv.setItem(44, glaspane);
		inv.setItem(49, glaspane);

		if (api.Kitsyml.get("Kits." + kit + ".armor.helmet") instanceof ItemStack
				|| api.Kitsyml.get("Kits." + kit + ".armor.helmet") != null) {
			ItemStack helmet = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.helmet");
			inv.setItem(50, helmet);
		} else {
			ItemStack helmet = new ItemStack(Material.IRON_BARS);
			ItemMeta hm = helmet.getItemMeta();
			hm.setDisplayName(Chat.format("&cHelmet"));
			helmet.setItemMeta(hm);
			inv.setItem(50, helmet);
		}
		if (api.Kitsyml.get("Kits." + kit + ".armor.chestplate") instanceof ItemStack
				|| api.Kitsyml.get("Kits." + kit + ".armor.chestplate") != null) {
			ItemStack chestplate = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.chestplate");
			inv.setItem(51, chestplate);
		} else {
			ItemStack chestplate = new ItemStack(Material.IRON_BARS);
			ItemMeta hm = chestplate.getItemMeta();
			hm.setDisplayName(Chat.format("&cChestplate"));
			chestplate.setItemMeta(hm);
			inv.setItem(51, chestplate);
		}
		if (api.Kitsyml.get("Kits." + kit + ".armor.leggings") instanceof ItemStack
				|| api.Kitsyml.get("Kits." + kit + ".armor.leggings") != null) {
			ItemStack leggings = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.leggings");
			inv.setItem(52, leggings);
		} else {
			ItemStack leggings = new ItemStack(Material.IRON_BARS);
			ItemMeta hm = leggings.getItemMeta();
			hm.setDisplayName(Chat.format("&cLeggings"));
			leggings.setItemMeta(hm);
			inv.setItem(52, leggings);
		}
		if (api.Kitsyml.get("Kits." + kit + ".armor.boots") instanceof ItemStack
				|| api.Kitsyml.get("Kits." + kit + ".armor.boots") != null) {
			ItemStack boots = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.boots");
			inv.setItem(53, boots);
		} else {
			ItemStack boots = new ItemStack(Material.IRON_BARS);
			ItemMeta hm = boots.getItemMeta();
			hm.setDisplayName(Chat.format("&cBoots"));
			boots.setItemMeta(hm);
			inv.setItem(53, boots);
		}

		ArrayList<ItemStack> content = (ArrayList<ItemStack>) api.Kitsyml.get("Kits." + kit + ".content");
		ItemStack[] items = content.toArray(new ItemStack[0]);
		for (ItemStack item : items) {
			inv.addItem(item);
		}

		p.openInventory(inv);

	}
	
	

}
