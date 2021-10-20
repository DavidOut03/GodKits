package me.godkits.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import me.godkits.Format.Time;
import me.godkits.Messages.Messages;

public class GUI_KitSettings implements Listener {

	static HashMap<Player, String> typepermission = new HashMap<>();
	static HashMap<Player, String> typecooldown = new HashMap<>();

	public static void sendGUI(Player p, String kit) {
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		String guiname = "kit-editor";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 45,
					Chat.format(plugin.getConfig().getString("GUI." + guiname + ".gui-name")));

			ItemStack sign = new ItemStack(Material.LEGACY_SIGN);
			ItemMeta signmeta = sign.getItemMeta();
			ArrayList<String> signlore = new ArrayList<>();
			signmeta.setDisplayName(Chat.format("&e" + kit));
			signlore.add(Chat.format("&7Current kit: &e" + kit));
			signmeta.setLore(signlore);
			sign.setItemMeta(signmeta);
			inv.setItem(22, sign);

			ItemStack pearl = new ItemStack(Material.ENDER_EYE);
			ItemMeta pearlmeta = pearl.getItemMeta();
			ArrayList<String> pearllore = new ArrayList<>();
			pearlmeta.setDisplayName(Chat.format("&c&lSet kit Permission"));
			pearllore.add(Chat.format("&7Click to set the kit Permission."));
			pearllore.add(Chat.format("&7Set permission to 'none' for no permission."));
			pearllore.add(
					Chat.format("&7Current permission: &c" + api.Kitsyml.get("Kits." + kit + ".permission") + "&7."));
			pearlmeta.setLore(pearllore);
			pearl.setItemMeta(pearlmeta);
			inv.setItem(20, pearl);

			ItemStack itemframe = new ItemStack(Material.ITEM_FRAME);
			ItemMeta framemeta = itemframe.getItemMeta();
			ArrayList<String> framelore = new ArrayList<>();
			framelore.add(Chat.format("&7Click to edit the gui icon."));
			framemeta.setDisplayName(Chat.format("&b&lEdit GUI Icon"));
			framemeta.setLore(framelore);
			itemframe.setItemMeta(framemeta);
			inv.setItem(23, itemframe);

			ItemStack clock = new ItemStack(Material.DAYLIGHT_DETECTOR);
			ItemMeta clockmeta = clock.getItemMeta();
			ArrayList<String> clocklore = new ArrayList<>();
			clockmeta.setDisplayName(Chat.format("&d&lSet kit Cooldown"));
			clocklore.add(Chat.format("&7Click to set the kit Cooldown."));
			clocklore.add(Chat.format("&7Set cooldown to '-1' for no cooldown."));
			clocklore.add(Chat.format(
					"&7Current cooldown: &d" + api.Kitsyml.getString("Kits." + kit + ".cooldown") + " &7seconds."));
			clockmeta.setLore(clocklore);
			clock.setItemMeta(clockmeta);
			inv.setItem(21, clock);

			ItemStack hopper = new ItemStack(Material.CHEST);
			ItemMeta hoppermeta = hopper.getItemMeta();
			ArrayList<String> hopperlore = new ArrayList<>();
			hopperlore.add(Chat.format("&7Click to edit the kit items."));
			hoppermeta.setLore(hopperlore);
			hoppermeta.setDisplayName(Chat.format("&3&lKit Items"));
			hopper.setItemMeta(hoppermeta);
			inv.setItem(24, hopper);

			for (int slot = 10; slot < 17; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			for (int slot = 19; slot < 26; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
					inv.setItem(slot, pane);
				}
			}

			for (int slot = 28; slot < 35; slot++) {
				if (inv.getItem(slot) == null || inv.getItem(slot).getType() == Material.AIR) {
					ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
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
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		if (e.getWhoClicked() instanceof Player) {
			if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
				if (e.getClickedInventory() != p.getInventory()) {
					if (e.getView().getTitle()
							.equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.kit-editor.gui-name")))) {
						if (e.getCurrentItem().getType() != Material.AIR) {
							if (e.getCurrentItem().getType() == Material.ENDER_EYE) {
								p.sendMessage(Messages.getMessage("TYPE_PERMISSION"));
								typepermission.put(p, ChatColor.stripColor(
										e.getClickedInventory().getItem(22).getItemMeta().getDisplayName()));
								e.setCancelled(true);
								p.closeInventory();
							} else if (e.getCurrentItem().getType() == Material.ITEM_FRAME) {
								String kit = ChatColor
										.stripColor(e.getClickedInventory().getItem(22).getItemMeta().getDisplayName());

								e.setCancelled(true);
								GUI_SetUpIcon.sendGUI(p, kit);

							} else if (e.getCurrentItem().getType() == Material.CHEST) {

								String kit = ChatColor
										.stripColor(e.getClickedInventory().getItem(22).getItemMeta().getDisplayName());
								e.setCancelled(true);
								GUI_SetUpKitInventory.sendEditorGUI(p, kit);
							} else if (e.getCurrentItem().getType() == Material.DAYLIGHT_DETECTOR) {
								String kit = ChatColor
										.stripColor(e.getClickedInventory().getItem(22).getItemMeta().getDisplayName());
								e.setCancelled(true);
								p.closeInventory();
								typecooldown.put(p, kit);
								p.sendMessage(Messages.getMessage("TYPE_COOLDOWN"));
							} else {
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
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		if (typecooldown.containsKey(e.getPlayer())) {
			if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL") || e.getMessage().equals("Cancel")) {
				typecooldown.remove(e.getPlayer());
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
			} else {
				e.setCancelled(true);
				if (checkInt(e.getPlayer(), e.getMessage()) == true) {
					try {
						int cooldown = Integer.parseInt(e.getMessage());
						e.getPlayer()
								.sendMessage(Messages.getMessage("SET_KIT_COOLDOWN")
										.replace("%player%", e.getPlayer().getName())
										.replace("%kit%", typecooldown.get(e.getPlayer()))
										.replace("%cooldown%", Time.format(cooldown)));
						api.Kitsyml.set("Kits." + typecooldown.get(e.getPlayer()) + ".cooldown", cooldown);
						api.Kitsyml.save(api.KitsFile);
						typecooldown.remove(e.getPlayer());
						plugin.reloadConfig();

					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					e.getPlayer().sendMessage(Chat.format("&c'" + e.getMessage() + "' is not a number."));
				}
			}
		} else if (typepermission.containsKey(e.getPlayer())) {
			if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL") || e.getMessage().equals("Cancel")) {
				typepermission.remove(e.getPlayer());
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
			} else {
				e.setCancelled(true);
				try {
					e.getPlayer()
							.sendMessage(Messages.getMessage("SET_KIT_PERMISSION")
									.replace("%player%", e.getPlayer().getName())
									.replace("%kit%", typepermission.get(e.getPlayer()))
									.replace("%permission%", e.getMessage()));
					api.Kitsyml.set("Kits." + typepermission.get(e.getPlayer()) + ".permission", e.getMessage());
					api.Kitsyml.save(api.KitsFile);
					typepermission.remove(e.getPlayer());
					plugin.reloadConfig();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	public static boolean checkInt(Player p, String message) {
		try {
			return true;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			p.sendMessage(Chat.format("&c'" + message + "' is not a number."));
			return false;
		}
	}

}
