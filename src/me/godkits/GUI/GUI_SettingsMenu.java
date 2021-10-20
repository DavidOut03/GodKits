package me.godkits.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SettingsMenu implements Listener {

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		String guiname = "settings";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 45,
					Chat.format(plugin.getConfig().getString("GUI.settings.gui-name")));

			ItemStack gui = new ItemStack(Material.CHEST);
			ItemMeta guim = gui.getItemMeta();
			ArrayList<String> guilore = new ArrayList<>();
			guim.setDisplayName(Chat.format("&cGUI"));
			guilore.add(Chat.format("&7Edit the gui names."));
			guim.setLore(guilore);
			gui.setItemMeta(guim);
			inv.setItem(20, gui);

			ItemStack other = new ItemStack(Material.COMPASS);
			ItemMeta om = other.getItemMeta();
			ArrayList<String> ol = new ArrayList<>();
			om.setDisplayName(Chat.format("&6Other Settings"));
			ol.add(Chat.format("&7- Edit the plugin prefix."));
			ol.add(Chat.format("&7- Enable or disable the kit gui."));
			ol.add(Chat.format("&7- Set the default kit."));
			ol.add(Chat.format("&7- Enable or disable sounds."));
			ol.add(Chat.format("&7- Edit the time format."));
			om.setLore(ol);
			other.setItemMeta(om);
			inv.setItem(22, other);

			ItemStack messages = new ItemStack(Material.NAME_TAG);
			ItemMeta mesm = messages.getItemMeta();
			ArrayList<String> mlore = new ArrayList<>();
			mesm.setDisplayName(Chat.format("&eMessages"));
			mlore.add(Chat.format("&7Edit the plugin messages."));
			mesm.setLore(mlore);
			messages.setItemMeta(mesm);
			inv.setItem(24, messages);

			for (int slot = 10; slot < 17; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			for (int slot = 19; slot < 26; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			for (int slot = 28; slot < 35; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}
			for (int slot = 0; slot < 45; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		Main plugin = JavaPlugin.getPlugin(Main.class);
		if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if (e.getWhoClicked() instanceof Player) {
				if (e.getClickedInventory() != p.getInventory()) {
					if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR) {
						if (e.getView().getTitle() != null) {
							if (e.getView().getTitle().equalsIgnoreCase(
									Chat.format(plugin.getConfig().getString("GUI.settings.gui-name")))) {
								e.setCancelled(true);
								if (e.getCurrentItem().getType() == Material.CHEST) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&cGUI"))) {
										e.setCancelled(true);
										p.closeInventory();
										GUI_SetUpGUI.sendGUI(p);
									}
								} else if (e.getCurrentItem().getType() == Material.NAME_TAG) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&eMessages"))) {
										e.setCancelled(true);
										p.closeInventory();
										GUI_SetUpMessages.sendFirstPageGUI(p);
									}
								} else if (e.getCurrentItem().getType() == Material.COMPASS) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&6Other Settings"))) {
										e.setCancelled(true);
										p.closeInventory();
										GUI_SetUpExtraSettings.sendGUI(p);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
