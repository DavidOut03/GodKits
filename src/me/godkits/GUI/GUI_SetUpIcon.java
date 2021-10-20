package me.godkits.GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
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

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpIcon implements Listener {

	static HashMap<Player, String> typeloretoadd = new HashMap<>();
	static HashMap<Player, String> typeloretoremove = new HashMap<>();
	static HashMap<Player, String> typedisplayname = new HashMap<>();

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		Player p = (Player) e.getWhoClicked();

		if (e.getWhoClicked() instanceof Player) {
			if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
				if (e.getClickedInventory() != p.getInventory()) {
					if (api.Kitsyml.getConfigurationSection("Kits.")
							.contains(ChatColor.stripColor(e.getView().getTitle()))) {

						if (e.getSlot() == 10) {
							p.closeInventory();
							if (e.getClick() == ClickType.LEFT) {
								typeloretoadd.put(p, ChatColor.stripColor(e.getView().getTitle()));
								p.sendMessage(Messages.getMessage("TYPE_LORE_TO_ADD"));
								p.sendMessage(Messages.getMessage("CANCEL"));
							} else if (e.getClick() == ClickType.RIGHT) {
								typeloretoremove.put(p, ChatColor.stripColor(e.getView().getTitle()));
								p.sendMessage(Messages.getMessage("TYPE_LORE_TO_REMOVE"));
								p.sendMessage(Messages.getMessage("CANCEL"));
							}
							e.setCancelled(true);
						} else if (e.getSlot() == 13) {
							if (e.getCursor().getType() != Material.AIR) {

								String kit = ChatColor.stripColor(e.getView().getTitle());

								ItemStack newicon = new ItemStack(e.getCursor().getType(), 1,
										e.getCursor().getData().getData());
								ItemMeta iconmeta = newicon.getItemMeta();
								ArrayList<String> lore = new ArrayList<>();

								lore.add(Chat.format("&7Put here the new icon."));
								iconmeta.setLore(lore);
								iconmeta.setDisplayName(Chat.format("&b&LKit Icon"));
								newicon.setItemMeta(iconmeta);
								e.setCurrentItem(newicon);

								try {
									api.Kitsyml.set("Kits." + kit + ".GUI.icon", e.getCursor().getType().toString());
									api.Kitsyml.set("Kits." + kit + ".GUI.iconID", e.getCursor().getData().getData());
									api.Kitsyml.save(api.KitsFile);
								} catch (IOException ex) {
									ex.printStackTrace();
								}
								e.setCursor(new ItemStack(Material.AIR));
							}
							e.setCancelled(true);
						} else if (e.getSlot() == 16) {
							p.closeInventory();
							typedisplayname.put(p, ChatColor.stripColor(e.getView().getTitle()));
							p.sendMessage(Messages.getMessage("TYPE_DISPLAYNAME"));
							p.sendMessage(Messages.getMessage("CANCEL"));
							e.setCancelled(true);
						} else {
							e.setCancelled(true);
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void onchat(AsyncPlayerChatEvent e) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		if (typeloretoadd.containsKey(e.getPlayer())) {
			if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL") || e.getMessage().equals("Cancel")) {
				typeloretoadd.remove(e.getPlayer());
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
			} else {
				e.setCancelled(true);
				String kit = typeloretoadd.get(e.getPlayer());
				try {
					ArrayList<String> lore = (ArrayList<String>) api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					lore.add(Chat.format(e.getMessage()));
					api.Kitsyml.set("Kits." + kit + ".GUI.lore", lore);
					api.Kitsyml.save(api.KitsFile);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				e.getPlayer().sendMessage(
						Messages.getMessage("ADDED_iCON_lORE").replace("%lore%", e.getMessage()).replace("%kit%", kit));
				typeloretoadd.remove(e.getPlayer());
			}
		} else if (typeloretoremove.containsKey(e.getPlayer())) {
			if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL") || e.getMessage().equals("Cancel")) {
				typeloretoremove.remove(e.getPlayer());
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
			} else {
				e.setCancelled(true);
				String kit = typeloretoremove.get(e.getPlayer());
				try {
					ArrayList<String> lore = (ArrayList<String>) api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					lore.remove(Chat.format(e.getMessage()));
					api.Kitsyml.set("Kits." + kit + ".GUI.lore", lore);
					api.Kitsyml.save(api.KitsFile);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				e.getPlayer().sendMessage(Messages.getMessage("REMOVED_ICON_LORE").replace("%lore%", e.getMessage())
						.replace("%kit%", kit));
				typeloretoremove.remove(e.getPlayer());
			}
		} else if (typedisplayname.containsKey(e.getPlayer())) {
			if (e.getMessage().equals("cancel") || e.getMessage().equals("CANCEL") || e.getMessage().equals("Cancel")) {
				typedisplayname.remove(e.getPlayer());
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						Messages.getMessage("CANCELED_ACTION").replace("%player%", e.getPlayer().getName()));
			} else {
				e.setCancelled(true);
				String kit = typedisplayname.get(e.getPlayer());
				try {
					api.Kitsyml.set("Kits." + kit + ".GUI.displayname", Chat.format(e.getMessage()));
					api.Kitsyml.save(api.KitsFile);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				e.getPlayer().sendMessage(Messages.getMessage("SET_ICON_DISPLAYNAME")
						.replace("%displayname%", e.getMessage()).replace("%kit%", kit));
				typedisplayname.remove(e.getPlayer());
			}

		}
	}

	public static void sendGUI(Player p, String kit) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		typedisplayname.remove(p);
		typeloretoadd.remove(p);
		typeloretoremove.remove(p);

		File KitsFile = new File("" + plugin.getDataFolder() + "//Kits.yml");
		YamlConfiguration Kitsyml = YamlConfiguration.loadConfiguration(KitsFile);

		if (KitsFile.exists()) {

			Inventory inv = Bukkit.createInventory(null, 27, Chat.format(ChatColor.YELLOW + kit));

			ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			inv.setItem(0, black);
			inv.setItem(2, black);
			inv.setItem(6, black);
			inv.setItem(8, black);
			inv.setItem(18, black);
			inv.setItem(20, black);
			inv.setItem(24, black);
			inv.setItem(26, black);

			ItemStack dark_green = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
			inv.setItem(1, dark_green);
			inv.setItem(7, dark_green);
			inv.setItem(9, dark_green);
			inv.setItem(11, dark_green);
			inv.setItem(15, dark_green);
			inv.setItem(17, dark_green);
			inv.setItem(19, dark_green);
			inv.setItem(25, dark_green);

			ItemStack lime = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			inv.setItem(4, lime);
			inv.setItem(12, lime);
			inv.setItem(14, lime);
			inv.setItem(22, lime);

			ItemStack gray = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			inv.setItem(3, gray);
			inv.setItem(5, gray);
			inv.setItem(21, gray);
			inv.setItem(23, gray);

			ItemStack lore = new ItemStack(Material.LEAD);
			ItemMeta lorem = lore.getItemMeta();
			ArrayList<String> ll = new ArrayList<>();
			lorem.setDisplayName(Chat.format("&a&lADD&7&l/&c&lRemove &eLore"));
			ll.add(Chat.format("&7Left-Click to add lore."));
			ll.add(Chat.format("&7Right-Click to remove lore."));
			ll.add(Chat.format("&7Current lore:"));
			List<String> configlore = Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
			for (String line : configlore) {
				ll.add(Chat.format(line));
			}
			lorem.setLore(ll);
			lore.setItemMeta(lorem);
			inv.setItem(10, lore);

			if (checkInt(Kitsyml.getString("Kits." + kit + ".GUI.icon")) == true) {
				ItemStack icon = new ItemStack(Material.getMaterial(Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,
						(short) Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
				ItemMeta iconmeta = icon.getItemMeta();
				List<String> iconlore = new ArrayList<>();
				iconlore.add(Chat.format("&7Put here the new icon."));
				iconmeta.setDisplayName(Chat.format("&b&lKit Icon"));
				iconmeta.setLore(iconlore);
				icon.setItemMeta(iconmeta);
				inv.setItem(13, icon);

			} else if (checkInt(Kitsyml.getString("Kits." + kit + ".GUI.icon")) == false) {
				ItemStack icon = new ItemStack(Material.getMaterial(Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,
						(short) Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
				ItemMeta iconmeta = icon.getItemMeta();
				List<String> iconlore = new ArrayList<>();
				iconlore.add(Chat.format("&7Put here the new icon."));
				iconmeta.setDisplayName(Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
				iconmeta.setLore(iconlore);
				icon.setItemMeta(iconmeta);
				inv.setItem(13, icon);

			}

			ItemStack displayname = new ItemStack(Material.NAME_TAG);
			ItemMeta dm = displayname.getItemMeta();
			dm.setDisplayName(Chat.format("&e&lSet Displayname"));
			ArrayList<String> dlore = new ArrayList<>();
			dlore.add(Chat.format("&7Click to chance the displayname."));
			dlore.add(Chat.format("&7Current displayname: " + Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
			dm.setLore(dlore);
			displayname.setItemMeta(dm);
			inv.setItem(16, displayname);

			p.openInventory(inv);

		} else {
			p.sendMessage(Chat.format("&cFiles not loaded reload server."));
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
