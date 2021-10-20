package me.godkits.GUI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class GUI_SetUpExtraSettings implements Listener {

	public static ArrayList<String> typeprefix = new ArrayList<>();
	public static ArrayList<String> typetimeformat = new ArrayList<>();

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "other-settings";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 27,
					Chat.format(plugin.getConfig().getString("GUI.other-settings.gui-name")));

			ItemStack pre = new ItemStack(Material.PAPER);
			ItemMeta prm = pre.getItemMeta();
			prm.setDisplayName(Chat.format("&dSet Prefix"));
			ArrayList<String> prl = new ArrayList<>();
			prl.add(Chat.format("&7Click to set the new plugin prefix."));
			prl.add(Chat.format("&7Current prefix: " + api.getPrefix()));
			prm.setLore(prl);
			pre.setItemMeta(prm);
			inv.setItem(11, pre);

			ItemStack sounds = new ItemStack(Material.NOTE_BLOCK);
			ItemMeta sm = sounds.getItemMeta();
			ArrayList<String> sl = new ArrayList<>();
			if (plugin.getConfig().getBoolean("Settings.sounds-enabled") == true) {
				sm.setDisplayName(Chat.format("&6Disable Sounds"));
				sl.add(Chat.format("&7Click to disable the plugin sounds."));
			} else if (plugin.getConfig().getBoolean("Settings.sounds-enabled") == false) {
				sm.setDisplayName(Chat.format("&eEnable Sounds"));
				sl.add(Chat.format("&7Click to enable the plugin sounds."));
			}
			sm.setLore(sl);
			sounds.setItemMeta(sm);
			inv.setItem(12, sounds);

			ItemStack format = new ItemStack(Material.MAP);
			ItemMeta fm = format.getItemMeta();
			fm.setDisplayName(Chat.format("&bTime-Format"));
			ArrayList<String> fl = new ArrayList<>();
			fl.add(Chat.format("&7Click to edit the time format."));
			fl.add(Chat.format("&7Current format: "));
			fl.add(Chat.format("&e" + plugin.getConfig().getString("Settings.time-format")));
			fm.setLore(fl);
			format.setItemMeta(fm);
			inv.setItem(13, format);

			ItemStack gui = new ItemStack(Material.HOPPER);
			ItemMeta gm = gui.getItemMeta();
			ArrayList<String> glore = new ArrayList<>();
			if (plugin.getConfig().getBoolean("Settings.gui-enabled") == true) {
				gm.setDisplayName(Chat.format("&cDisable GUI"));
				glore.add(Chat.format("&7Click to disable the kit gui."));
			} else if (plugin.getConfig().getBoolean("Settings.gui-enabled") == false) {
				gm.setDisplayName(Chat.format("&aEnable GUI"));
				glore.add(Chat.format("&7Click to enable the kit gui."));
			}
			gm.setLore(glore);
			gui.setItemMeta(gm);
			inv.setItem(14, gui);

			ItemStack dk = new ItemStack(Material.CHEST);
			ItemMeta dkm = dk.getItemMeta();
			dkm.setDisplayName(Chat.format("&8Default Kit"));
			ArrayList<String> dklore = new ArrayList<>();
			dklore.add(Chat.format("&7Click to set the new default kit."));
			dklore.add(Chat.format("&7Set the kit to &e'none' &7if you want no default kit."));
			dklore.add(Chat.format("&7Current default kit: &6" + plugin.getConfig().getString("Settings.default-kit")));
			dkm.setLore(dklore);
			dk.setItemMeta(dkm);
			inv.setItem(15, dk);

			ItemStack back = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta backmeta = back.getItemMeta();
			ArrayList<String> backlore = new ArrayList<>();
			backlore.add(Chat.format("&7Click to go back to the settings gui."));
			backmeta.setLore(backlore);
			backmeta.setDisplayName(Chat.format("&c&lBack"));
			back.setItemMeta(backmeta);
			inv.setItem(22, back);

			for (int slot = 10; slot < 17; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}
			for (int slot = 0; slot < 27; slot++) {
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
			if (e.getView().getTitle() != null) {
				if (e.getWhoClicked() instanceof Player) {
					if (e.getView().getTitle().equalsIgnoreCase(
							Chat.format(plugin.getConfig().getString("GUI.other-settings.gui-name")))) {
						if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR) {
							if (e.getCurrentItem().getItemMeta() != null
									|| e.getCurrentItem().getItemMeta().getDisplayName() != null) {

								if (e.getCurrentItem().getType() == Material.PAPER) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&dSet Prefix"))) {
										e.setCancelled(true);
										p.closeInventory();
										typeprefix.add(p.getName());
										p.sendMessage(Messages.getMessage("TYPE_PREFIX"));
										p.sendMessage(Messages.getMessage("CANCEL"));
									}
								} else if (e.getCurrentItem().getType() == Material.HOPPER) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&cDisable GUI"))) {
										e.getClickedInventory().remove(e.getCurrentItem());

										ItemStack gui = new ItemStack(Material.HOPPER);
										ItemMeta gm = gui.getItemMeta();
										ArrayList<String> glore = new ArrayList<>();
										gm.setDisplayName(Chat.format("&aEnable GUI"));
										glore.add(Chat.format("&7Click to enable the kit gui."));
										gm.setLore(glore);
										gui.setItemMeta(gm);
										e.getClickedInventory().setItem(e.getSlot(), gui);
										plugin.getConfig().set("Settings.gui-enabled", false);
										plugin.saveConfig();
										plugin.reloadConfig();
										p.sendMessage(
												Messages.getMessage("DISABLED_GUI").replace("%player%", p.getName()));
									} else if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&aEnable GUI"))) {
										e.getClickedInventory().remove(e.getCurrentItem());

										ItemStack gui = new ItemStack(Material.HOPPER);
										ItemMeta gm = gui.getItemMeta();
										ArrayList<String> glore = new ArrayList<>();
										gm.setDisplayName(Chat.format("&cDisable GUI"));
										glore.add(Chat.format("&7Click to disable the kit gui."));
										gm.setLore(glore);
										gui.setItemMeta(gm);
										e.getClickedInventory().setItem(e.getSlot(), gui);
										plugin.getConfig().set("Settings.gui-enabled", true);
										plugin.saveConfig();
										plugin.reloadConfig();
										p.sendMessage(
												Messages.getMessage("ENABLED_GUI").replace("%player%", p.getName()));
									}
									e.setCancelled(true);
								} else if (e.getCurrentItem().getType() == Material.NOTE_BLOCK) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&6Disable Sounds"))) {
										e.getClickedInventory().remove(e.getCurrentItem());

										ItemStack gui = new ItemStack(Material.NOTE_BLOCK);
										ItemMeta gm = gui.getItemMeta();
										ArrayList<String> glore = new ArrayList<>();
										gm.setDisplayName(Chat.format("&eEnable Sounds"));
										glore.add(Chat.format("&7Click to enable the plugin sounds."));
										gm.setLore(glore);
										gui.setItemMeta(gm);
										e.getClickedInventory().setItem(e.getSlot(), gui);
										plugin.getConfig().set("Settings.sounds-enabled", false);
										plugin.saveConfig();
										plugin.reloadConfig();
										p.sendMessage(Messages.getMessage("DISABLED_SOUNDS").replace("%player%",
												p.getName()));
										String s = plugin.getConfig().getString("Sounds.disabled-sounds.sound");
										String v = plugin.getConfig().getString("Sounds.disabled-sounds.volume");
										String pi = plugin.getConfig().getString("Sounds.disabled-sounds.pitch");
										if (isSound(s)) {
											if (isINT(v)) {
												if (isINT(pi)) {
													GodKitsApi api = new GodKitsApi();
													Sound sound = Sound.valueOf(plugin.getConfig()
															.getString("Sounds.disabled-sounds.sound"));
													int volume = plugin.getConfig()
															.getInt("Sounds.disabled-sounds.volume");
													int pitch = plugin.getConfig()
															.getInt("Sounds.disabled-sounds.pitch");
													api.PlaySound(p, sound, volume, pitch);
												}
											}
										}
									} else if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&eEnable Sounds"))) {
										e.getClickedInventory().remove(e.getCurrentItem());

										ItemStack gui = new ItemStack(Material.NOTE_BLOCK);
										ItemMeta gm = gui.getItemMeta();
										ArrayList<String> glore = new ArrayList<>();
										gm.setDisplayName(Chat.format("&6Disable Sounds"));
										glore.add(Chat.format("&7Click to disable the plugin sounds."));
										gm.setLore(glore);
										gui.setItemMeta(gm);
										e.getClickedInventory().setItem(e.getSlot(), gui);
										plugin.getConfig().set("Settings.sounds-enabled", true);
										plugin.saveConfig();
										plugin.reloadConfig();
										p.sendMessage(
												Messages.getMessage("ENABLED_SOUNDS").replace("%player%", p.getName()));
										String s = plugin.getConfig().getString("Sounds.enabled-sounds.sound");
										String v = plugin.getConfig().getString("Sounds.enabled-sounds.volume");
										String pi = plugin.getConfig().getString("Sounds.enabled-sounds.pitch");
										if (isSound(s)) {
											if (isINT(v)) {
												if (isINT(pi)) {
													GodKitsApi api = new GodKitsApi();
													Sound sound = Sound.valueOf(plugin.getConfig()
															.getString("Sounds.enabled-sounds.sound"));
													int volume = plugin.getConfig()
															.getInt("Sounds.enabled-sounds.volume");
													int pitch = plugin.getConfig()
															.getInt("Sounds.enabled-sounds.pitch");
													api.PlaySound(p, sound, volume, pitch);
												}
											}
										}
									}
									e.setCancelled(true);
								} else if (e.getCurrentItem().getType() == Material.CHEST) {
									if (e.getCurrentItem().getItemMeta().getDisplayName()
											.equalsIgnoreCase(Chat.format("&8Default Kit"))) {
										e.setCancelled(true);
										p.closeInventory();
										GUI_SetUpDefaultKit.sendGUI(p);

									}
								} else if (e.getCurrentItem().getType() == Material.MAP) {
									e.setCancelled(true);
									p.closeInventory();
									typetimeformat.add(p.getName());
									p.sendMessage(
											Messages.getMessage("TYPE_TIME_FORMAT").replace("%player%", p.getName()));
									p.sendMessage(Messages.getMessage("CANCEL"));

								} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
									e.setCancelled(true);
									p.closeInventory();
									GUI_SettingsMenu.sendGUI(p);
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

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		Player p = e.getPlayer();

		if (e.getMessage() != null) {
			if (typeprefix.contains(e.getPlayer().getName())) {
				if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL")
						|| e.getMessage().equals("Cancel")) {
					typeprefix.remove(e.getPlayer().getName());
					e.setCancelled(true);
					p.sendMessage(Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
				} else {
					plugin.getConfig().set("Settings.prefix", e.getMessage());
					plugin.saveConfig();
					plugin.reloadConfig();
					p.sendMessage(Messages.getMessage("SET_PREFIX").replace("%newprefix%",
							Chat.format(e.getMessage()).replace("%player%", p.getName())));
					typeprefix.remove(e.getPlayer().getName());
					e.setCancelled(true);
				}
			} else if (typetimeformat.contains(e.getPlayer().getName())) {
				if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL")
						|| e.getMessage().equals("Cancel")) {
					typetimeformat.remove(e.getPlayer().getName());
					e.setCancelled(true);
					p.sendMessage(Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
				} else {
					plugin.getConfig().set("Settings.time-format", e.getMessage());
					plugin.saveConfig();
					plugin.reloadConfig();
					p.sendMessage(Messages.getMessage("SET_TIME_FORMAT").replace("%newTimeFormat%",
							Chat.format(e.getMessage()).replace("%player%", p.getName())));
					typetimeformat.remove(e.getPlayer().getName());
					e.setCancelled(true);
				}
			}
		}
	}

	public static boolean isSound(String message) {
		try {
			Sound.valueOf(message);
			return true;
		} catch (IllegalArgumentException ex) {
			Chat.format(Messages.getMessage("NOT_A_SOUND"));
			return false;
		}
	}

	public static boolean isINT(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException ex) {
			Chat.format("&c" + message + " is not a number.");
			return false;
		}
	}

}
