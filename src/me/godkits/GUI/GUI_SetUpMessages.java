package me.godkits.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpMessages implements Listener {

	HashMap<Player, String> typemessage = new HashMap<>();

	public static void sendFirstPageGUI(Player p) {
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		String guiname = "messages";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 54,
					Chat.format(plugin.getConfig().getString("GUI.messages.gui-name")));

			for (int slot = 0; slot < 9; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			int place = 1;
			Set<String> section = api.Messagesyml.getConfigurationSection("Messages").getKeys(false);
			for (String currentmessage : section) {
				if (place < 37) {
					ItemStack message = new ItemStack(Material.NAME_TAG);
					ItemMeta messagemeta = message.getItemMeta();
					ArrayList<String> messagelore = new ArrayList<>();
					messagelore.add(Chat.format("&7Current message: "));
					messagelore.add(Chat.format(Messages.getMessage(currentmessage)));
					messagemeta.setLore(messagelore);
					messagemeta.setDisplayName(Chat.format("&b" + currentmessage));
					message.setItemMeta(messagemeta);
					inv.addItem(message);
					place++;
				}

			}

			ItemStack page = new ItemStack(Material.PAPER);
			ItemMeta pagemeta = page.getItemMeta();
			ArrayList<String> pagelore = new ArrayList<>();
			pagelore.add(Chat.format("&7Click to go to the next page."));
			pagemeta.setLore(pagelore);
			pagemeta.setDisplayName(Chat.format("&aNext Page"));
			page.setItemMeta(pagemeta);
			inv.setItem(50, page);

			ItemStack back = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta backmeta = back.getItemMeta();
			ArrayList<String> backlore = new ArrayList<>();
			backlore.add(Chat.format("&7Click to go back to the settings gui."));
			backmeta.setLore(backlore);
			backmeta.setDisplayName(Chat.format("&c&lBack"));
			back.setItemMeta(backmeta);
			inv.setItem(48, back);

			for (int slot = 45; slot < 54; slot++) {
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

	public static void sendSecondPageGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "messages";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 54,
					Chat.format(plugin.getConfig().getString("GUI.messages.gui-name")));

			for (int slot = 0; slot < 9; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			int place = 1;
			Set<String> section = api.Messagesyml.getConfigurationSection("Messages").getKeys(false);
			for (String currentmessage : section) {
				if (place == 37 || place >= 37 && place < section.size()) {
					ItemStack message = new ItemStack(Material.NAME_TAG);
					ItemMeta messagemeta = message.getItemMeta();
					ArrayList<String> messagelore = new ArrayList<>();
					messagelore.add(Chat.format("&7Current message: "));
					messagelore.add(Chat.format(Messages.getMessage(currentmessage)));
					messagemeta.setLore(messagelore);
					messagemeta.setDisplayName(Chat.format("&b" + currentmessage));
					message.setItemMeta(messagemeta);
					inv.addItem(message);
					place++;
				} else {
					place++;
				}

			}

			for (int slot = 9; slot < 45; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.IRON_BARS);
					inv.setItem(slot, pane);
				}
			}

			ItemStack page = new ItemStack(Material.PAPER);
			ItemMeta pagemeta = page.getItemMeta();
			ArrayList<String> pagelore = new ArrayList<>();
			pagelore.add(Chat.format("&7Click to go to the last page."));
			pagemeta.setLore(pagelore);
			pagemeta.setDisplayName(Chat.format("&cPast Page"));
			page.setItemMeta(pagemeta);
			inv.setItem(50, page);

			ItemStack back = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta backmeta = back.getItemMeta();
			ArrayList<String> backlore = new ArrayList<>();
			backlore.add(Chat.format("&7Click to go back to the settings gui."));
			backmeta.setLore(backlore);
			backmeta.setDisplayName(Chat.format("&c&lBack"));
			back.setItemMeta(backmeta);
			inv.setItem(48, back);

			for (int slot = 45; slot < 54; slot++) {
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
		GodKitsApi api = new GodKitsApi();

		if (e.getWhoClicked() instanceof Player) {
			if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
				if (e.getClickedInventory() != p.getInventory()) {
					if (e.getView().getTitle()!= null) {
						if (e.getView().getTitle()
								.equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.messages.gui-name")))) {
							if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem() != null) {
								if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
									if (e.getCurrentItem().getType() == Material.PAPER) {
										if (e.getCurrentItem().getItemMeta().getDisplayName()
												.equalsIgnoreCase(Chat.format("&aNext Page"))) {
											e.setCancelled(true);
											p.closeInventory();
											sendSecondPageGUI(p);
										} else if (e.getCurrentItem().getItemMeta().getDisplayName()
												.equalsIgnoreCase(Chat.format("&cPast Page"))) {
											e.setCancelled(true);
											p.closeInventory();
											sendFirstPageGUI(p);
										}
									} else if (e.getCurrentItem().getType() == Material.NAME_TAG) {
										for (String currentmessage : api.Messagesyml.getConfigurationSection("Messages")
												.getKeys(false)) {
											if (currentmessage.equalsIgnoreCase(ChatColor
													.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) {
												e.setCancelled(true);
												p.closeInventory();
												typemessage.put(p, ChatColor
														.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
												p.sendMessage(Messages.getMessage("TYPE_MESSAGE").replace("%player%",
														p.getName()));
												p.sendMessage(Messages.getMessage("CANCEL"));
											}
										}
									} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
										e.setCancelled(true);
										p.closeInventory();
										GUI_SettingsMenu.sendGUI(p);
									}
								}
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage() != null) {
			if (typemessage.containsKey(p)) {
				if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL")
						|| e.getMessage().equals("Cancel")) {
					typemessage.remove(e.getPlayer());
					e.setCancelled(true);
					e.getPlayer().sendMessage(
							Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
				} else {
					GodKitsApi api = new GodKitsApi();
					e.setCancelled(true);
					try {
						api.Messagesyml.set("Messages." + typemessage.get(p), e.getMessage());
						api.Messagesyml.save(api.MessagesFile);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					p.sendMessage(Messages.getMessage("SET_MESSAGE").replace("%player%", p.getName())
							.replace("%message%", typemessage.get(p))
							.replace("%newmessage%", Chat.format(e.getMessage())));
					typemessage.remove(p);
				}
			}
		}
	}

}
