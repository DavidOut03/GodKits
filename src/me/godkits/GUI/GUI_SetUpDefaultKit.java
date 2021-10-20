package me.godkits.GUI;

import java.util.ArrayList;
import java.util.List;

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

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpDefaultKit implements Listener {

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "default-kit";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.kits.rows") * 9,
					Chat.format(plugin.getConfig().getString("GUI.default-kit.gui-name")));
			ArrayList<ItemStack> items = (ArrayList<ItemStack>) api.KitsGUIyml.get("GUI.content");
			ItemStack[] content = items.toArray(new ItemStack[0]);
			if (content.length <= inv.getSize() || content.length == inv.getSize()) {
				inv.setContents(content);
			} else {
				if (api.hasPermission(p, "godkits.errors")) {
					p.sendMessage(Messages.getMessage("ERROR_GUI_SIZE"));
				} else {
					p.sendMessage(Messages.getMessage("CONTACT_STAFF"));
				}
			}

			for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {

				if (checkInt(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")) == true) {
					ItemStack icon = new ItemStack(
							Material.getMaterial(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,
							(short) api.Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
					ItemMeta iconmeta = icon.getItemMeta();
					List<String> iconlore = api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					ArrayList<String> newlore = new ArrayList<>();
					for (String lore : iconlore) {
						newlore.add(Chat.format(lore).replace("%permission%",
								api.Kitsyml.getString("Kits." + kit + ".permission")));
					}
					iconmeta.setDisplayName(Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
					iconmeta.setLore(newlore);
					icon.setItemMeta(iconmeta);
					inv.addItem(icon);

				} else if (checkInt(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")) == false) {
					ItemStack icon = new ItemStack(
							Material.getMaterial(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,
							(short) api.Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
					ItemMeta iconmeta = icon.getItemMeta();
					List<String> iconlore = api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					ArrayList<String> newlore = new ArrayList<>();
					for (String lore : iconlore) {
						newlore.add(Chat.format(lore));
					}
					iconmeta.setDisplayName(Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
					iconmeta.setLore(newlore);
					icon.setItemMeta(iconmeta);
					inv.addItem(icon);

				}

			}

			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		Player p = (Player) e.getWhoClicked();

		if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if (e.getWhoClicked() != null) {
				if (e.getWhoClicked() instanceof Player) {
					if (e.getClickedInventory() != p.getInventory()) {
						if (e.getView().getTitle().equalsIgnoreCase(
								Chat.format(plugin.getConfig().getString("GUI.default-kit.gui-name")))) {
							e.setCancelled(true);
							if (e.getCurrentItem().getItemMeta() != null) {
								if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
									if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem() != null) {
										for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
											if (Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname"))
													.equalsIgnoreCase(Chat.format(
															e.getCurrentItem().getItemMeta().getDisplayName()))) {
												e.setCancelled(true);
												plugin.getConfig().set("Settings.default-kit", kit + "");
												plugin.saveConfig();
												plugin.reloadConfig();
												p.sendMessage(Messages.getMessage("SET_DEFAULT_KIT")
														.replace("%player%", p.getName()).replace("%kit%", kit));
												p.closeInventory();
											} else {
												e.setCancelled(true);
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
	}

	public static boolean checkInt(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
