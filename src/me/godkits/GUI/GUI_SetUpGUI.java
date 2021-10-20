package me.godkits.GUI;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpGUI implements Listener {

	HashMap<Player, String> guiname = new HashMap<>();

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		String guiname = "gui-editor";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 36,
					Chat.format(plugin.getConfig().getString("GUI.gui-editor.gui-name")));

			for (int slot = 0; slot < 9; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}
			ItemStack back = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta backmeta = back.getItemMeta();
			ArrayList<String> backlore = new ArrayList<>();
			backlore.add(Chat.format("&7Click to go back to the settings gui."));
			backmeta.setLore(backlore);
			backmeta.setDisplayName(Chat.format("&c&lBack"));
			back.setItemMeta(backmeta);
			inv.setItem(31, back);

			for (int slot = 27; slot < 36; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			for (String currentgui : plugin.getConfig().getConfigurationSection("GUI").getKeys(false)) {
				if (currentgui.equalsIgnoreCase("kits")) {
					ItemStack gui = new ItemStack(Material.HOPPER);
					ItemMeta guimeta = gui.getItemMeta();
					ArrayList<String> guilore = new ArrayList<>();
					guimeta.setDisplayName(Chat.format(plugin.getConfig().getString("GUI.kits.gui-name")));
					guilore.add(Chat.format("&7(&a!&7) Left-Click to edit the &e" + currentgui + "&7 gui name."));
					guilore.add(Chat.format("&7(&a!&7) Right-Click to edit the amount of gui slots."));
					guimeta.setLore(guilore);
					gui.setItemMeta(guimeta);
					inv.addItem(gui);
				} else {
					ItemStack gui = new ItemStack(Material.HOPPER);
					ItemMeta guimeta = gui.getItemMeta();
					ArrayList<String> guilore = new ArrayList<>();
					guimeta.setDisplayName(
							Chat.format(plugin.getConfig().getString("GUI." + currentgui + ".gui-name")));
					guilore.add(Chat.format("&7(&a!&7) Click to edit the &e" + currentgui + "&7 gui name."));
					guimeta.setLore(guilore);
					gui.setItemMeta(guimeta);
					inv.addItem(gui);
				}

			}

			for (int slot = 10; slot < 27; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.IRON_BARS, 1);
					inv.setItem(slot, pane);
				}
			}

			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}
	}

	public static void sendSlotGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		Inventory inv = Bukkit.createInventory(null, 9,
				Chat.format(plugin.getConfig().getString("GUI.set-rows.gui-name")));

		for (int slot = 0; slot < 6; slot++) {
			int amount = slot + 1;
			ItemStack rows = new ItemStack(Material.PAPER, amount);
			ItemMeta im = rows.getItemMeta();
			if (slot == 0) {
				im.setDisplayName(Chat.format("&c" + amount + " row"));
			} else {
				im.setDisplayName(Chat.format("&c" + amount + " rows"));
			}
			rows.setItemMeta(im);
			inv.setItem(slot, rows);
		}
		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if (e.getWhoClicked() instanceof Player) {
				if (e.getView().getTitle() != null) {
					if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem() != null) {
						if (e.getView().getTitle().equalsIgnoreCase(
								Chat.format(plugin.getConfig().getString("GUI.gui-editor.gui-name")))) {

							if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
								if (e.getCurrentItem().getType() == Material.HOPPER) {
									for (String gui : plugin.getConfig().getConfigurationSection("GUI")
											.getKeys(false)) {
										if (Chat.format(plugin.getConfig().getString("GUI." + gui + ".gui-name"))
												.equalsIgnoreCase(Chat
														.format(e.getCurrentItem().getItemMeta().getDisplayName()))) {
											if (Chat.format(e.getCurrentItem().getItemMeta().getDisplayName())
													.equalsIgnoreCase(Chat.format(
															plugin.getConfig().getString("GUI.kits.gui-name")))) {
												if (e.getClick() == ClickType.LEFT) {
													guiname.put(p, "kits");
													p.closeInventory();
													e.setCancelled(true);
													p.sendMessage(Messages.getMessage("TYPE_GUI_NAME"));
													p.sendMessage(Messages.getMessage("CANCEL"));
												} else if (e.getClick() == ClickType.RIGHT) {
													e.setCancelled(true);
													sendSlotGUI(p);
												}
											} else {
												guiname.put(p, gui);
												p.closeInventory();
												e.setCancelled(true);
												p.sendMessage(Messages.getMessage("TYPE_GUI_NAME"));
												p.sendMessage(Messages.getMessage("CANCEL"));
											}
										}
									}
								} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
									e.setCancelled(true);
									GUI_SettingsMenu.sendGUI(p);
								}
							}
							e.setCancelled(true);

						} else if (e.getView().getTitle()
								.equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.set-rows.gui-name")))) {
							if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem() != null) {
								e.setCancelled(true);
								p.closeInventory();
								plugin.getConfig().set("GUI.kits.rows", e.getCurrentItem().getAmount());
								plugin.saveConfig();
								plugin.reloadConfig();
								p.sendMessage(Messages.getMessage("SET_AMOUNT_OF_ROWS").replace("%player%", p.getName())
										.replace("%rows%", e.getCurrentItem().getAmount() + ""));
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		if (e.getMessage() != null) {
			if (e.getPlayer() != null) {
				if (guiname.containsKey(e.getPlayer())) {
					if (e.getMessage().equals("cancel")) {
						guiname.remove(e.getPlayer());
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage(
								Messages.getMessage("SET_GUI_NAME").replace("%gui%", guiname.get(e.getPlayer()))
										.replace("%name%", Chat.format(e.getMessage()))
										.replace("%player%", e.getPlayer().getName()));
						plugin.getConfig().set("GUI." + guiname.get(e.getPlayer()) + ".gui-name", e.getMessage());
						plugin.saveConfig();
						plugin.reloadConfig();
						guiname.remove(e.getPlayer());
					}

				}
			}
		}
	}

}
