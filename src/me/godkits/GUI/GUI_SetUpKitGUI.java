package me.godkits.GUI;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpKitGUI implements Listener {

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "kit-gui-editor";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.kits.rows") * 9,
					Chat.format(plugin.getConfig().getString("GUI." + guiname + ".gui-name")));

			if (api.KitsGUIFile != null) {
				ArrayList<ItemStack> items = (ArrayList<ItemStack>) api.KitsGUIyml.get("GUI.content");
				ItemStack[] content = items.toArray(new ItemStack[0]);
				if (content != null) {
					for (String item : api.KitsGUIyml.getConfigurationSection("GUI").getKeys(false)) {
						if (api.Kitsyml.get("Kits.") + item != null) {
							if (content.length <= inv.getSize() || content.length == inv.getSize()) {
								inv.setContents(content);
							} else {
								if (api.hasPermission(p, "godkits.errors")) {
									p.sendMessage(Messages.getMessage("ERROR_GUI_SIZE"));
								} else {
									p.sendMessage(Messages.getMessage("CONTACT_STAFF"));
								}

							}
						}
					}
				}
			}
			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {

		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "kit-gui-editor";
		if (e.getInventory() != e.getPlayer().getInventory()) {
			if (e.getView().getTitle()
					.equalsIgnoreCase(	Chat.format(plugin.getConfig().getString("GUI." + guiname + ".gui-name")))) {
				if (e.getPlayer() instanceof Player) {
					try {
						api.KitsGUIyml.set("GUI.content", e.getInventory().getContents());
						api.KitsGUIyml.save(api.KitsGUIFile);

					} catch (IOException ex) {
						ex.printStackTrace();
					}
					Player p = (Player) e.getPlayer();
					p.sendMessage(Messages.getMessage("KIT_SETGUI").replace("%player%", e.getPlayer().getName()));
				}
			}
		}
	}

}
