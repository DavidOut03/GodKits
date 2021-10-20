package me.godkits.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class Kits {

	public static void createKit(String kit) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		File KitsFile = api.KitsFile;
		YamlConfiguration Kitsyml = api.Kitsyml;

		if (KitsFile.exists()) {
			try {
				String none = "none";
				Kitsyml.set("Kits." + kit + "." + "permission", "" + none);
				Kitsyml.set("Kits." + kit + "." + "cooldown", -1);
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "icon", "PAPER");
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "iconID", 0);
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "displayname", "&8" + kit);

				ArrayList<String> lore = new ArrayList<>();
				lore.add(Chat.format("&7Kit Cooldown: &e%kitcooldown%"));
				lore.add(Chat.format("&7Avalible in : &c%cooldown%"));
				lore.add(Chat.format(" "));
				lore.add(Chat.format("&7(&e!&7) Right-Click to preview the kit."));
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "lore", lore);

				ArrayList<ItemStack> defaultitems = new ArrayList<>();
				defaultitems.add(new ItemStack(Material.STONE_SWORD));
				defaultitems.add(new ItemStack(Material.STONE_PICKAXE));
				defaultitems.add(new ItemStack(Material.STONE_AXE));
				defaultitems.add(new ItemStack(Material.COOKED_BEEF, 3));
				Kitsyml.set("Kits." + kit + ".content", defaultitems);

				Kitsyml.set("Kits." + kit + ".armor.helmet", new ItemStack(Material.LEATHER_HELMET));
				Kitsyml.set("Kits." + kit + ".armor.chestplate", new ItemStack(Material.LEATHER_CHESTPLATE));
				Kitsyml.set("Kits." + kit + ".armor.leggings", new ItemStack(Material.LEATHER_LEGGINGS));
				Kitsyml.set("Kits." + kit + ".armor.boots", new ItemStack(Material.LEATHER_BOOTS));

				Kitsyml.save(KitsFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void createKitPlayer(Player p, String kit) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		File KitsFile = new File(plugin.getDataFolder() + "//Kits.yml");
		YamlConfiguration Kitsyml = YamlConfiguration.loadConfiguration(KitsFile);

		if (KitsFile.exists()) {
			try {
				String none = "none";
				Kitsyml.set("Kits." + kit + "." + "permission", "" + none);
				Kitsyml.set("Kits." + kit + "." + "cooldown", -1);
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "icon", "PAPER");
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "iconID", 0);
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "displayname", "&8" + kit);

				ArrayList<String> lore = new ArrayList<>();
				lore.add(Chat.format("&7Kit Cooldown: &e%kitcooldown%"));
				lore.add(Chat.format("&7Avalible in : &c%cooldown%"));
				lore.add(Chat.format(" "));
				lore.add(Chat.format("&7(&e!&7) Right-Click to preview the kit."));
				Kitsyml.set("Kits." + kit + "." + "GUI" + "." + "lore", lore);

		
				ArrayList<ItemStack> defaultitems = new ArrayList<>();
				try {
					for (ItemStack item : p.getInventory().getStorageContents()) {
						if (item != null) {
							defaultitems.add(item);
						}				
					}
				} catch (NoSuchMethodError ex) {
					for (ItemStack item : p.getInventory().getContents()) {
						if (item != null) {
							defaultitems.add(item);
						}				
					}
				}

				Kitsyml.set("Kits." + kit + ".content", defaultitems);
				if (p.getInventory().getHelmet() != null) {
					Kitsyml.set("Kits." + kit + ".armor.helmet", p.getInventory().getHelmet());
				}
				if (p.getInventory().getChestplate() != null) {
					Kitsyml.set("Kits." + kit + ".armor.chestplate", p.getInventory().getChestplate());
				}
				if (p.getInventory().getLeggings() != null) {
					Kitsyml.set("Kits." + kit + ".armor.leggings", p.getInventory().getLeggings());
				}
				if (p.getInventory().getBoots() != null) {
					Kitsyml.set("Kits." + kit + ".armor.boots", p.getInventory().getBoots());
				}

				Kitsyml.save(KitsFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void removeKit(String kit) {
		JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();

		try {
			api.Kitsyml.set("Kits." + kit, null);
			api.Kitsyml.save(api.KitsFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void sendKit(Player p, String kit) {
		JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();

		if (api.Kitsyml.getConfigurationSection("Kits").contains(kit)) {

			ArrayList<ItemStack> items = (ArrayList<ItemStack>) api.Kitsyml.get("Kits." + kit + ".content");

			ItemStack[] content = items.toArray(new ItemStack[0]);

			if (api.Kitsyml.get("Kits." + kit + ".armor.helmet") != null || api.Kitsyml
					.getItemStack("Kits." + kit + ".armor.helmet") != new ItemStack(Material.IRON_BARS)) {
				if (p.getInventory().getHelmet() == null) {
					p.getInventory().setHelmet((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.helmet"));
				} else {
					if (p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.helmet"));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(),
								(ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.helmet"));
					}
				}
			}
			if (api.Kitsyml.get("Kits." + kit + ".armor.chestplate") != null || api.Kitsyml
					.getItemStack("Kits." + kit + ".armor.chestplate") != new ItemStack(Material.IRON_BARS)) {
				if (p.getInventory().getChestplate() == null) {
					p.getInventory().setChestplate((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.chestplate"));
				} else {
					if (p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.chestplate"));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(),
								(ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.chestplate"));
					}
				}
			}
			if (api.Kitsyml.get("Kits." + kit + ".armor.leggings") != null || api.Kitsyml
					.getItemStack("Kits." + kit + ".armor.leggings") != new ItemStack(Material.IRON_BARS)) {
				if (p.getInventory().getLeggings() == null) {
					p.getInventory().setLeggings((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.leggings"));
				} else {
					if (p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.leggings"));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(),
								(ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.leggings"));
					}
				}
			}

			if (api.Kitsyml.get("Kits." + kit + ".armor.boots") != null
					|| api.Kitsyml.getItemStack("Kits." + kit + ".armor.boots") != new ItemStack(Material.IRON_BARS)) {
				if (p.getInventory().getBoots() == null) {
					p.getInventory().setBoots((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.boots"));
				} else {
					if (p.getInventory().firstEmpty() != -1) {
						p.getInventory().addItem((ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.boots"));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(),
								(ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.boots"));
					}
				}
			}
			if (items != null && !items.isEmpty()) {

				p.sendMessage(
						Messages.getMessage("RECIEVED_KIT").replace("%player%", p.getName()).replace("%kit%", kit));

				if (p.getInventory().firstEmpty() != -1) {
					for (ItemStack item : content) {
						p.getInventory().addItem(item);
					}
				} else {
					p.sendMessage(Messages.getMessage("INVENTORY_FULL"));
					for (ItemStack item : content) {
						p.getWorld().dropItemNaturally(p.getLocation(), item);
					}
				}

			} else {
				p.sendMessage(Messages.getMessage("NO_ITEMS").replace("%kit%", kit));
			}
			if (!GodKitsApi.disabledsounds.contains(p.getName())) {
				Main plugin = JavaPlugin.getPlugin(Main.class);
				if (plugin.getConfig().getString("Sounds.recieved-kit.sound") != null
						&& plugin.getConfig().getString("Sounds.recieved-kit.volume") != null
						&& plugin.getConfig().getString("Sounds.recieved-kit.pitch") != null) {
					String s = plugin.getConfig().getString("Sounds.recieved-kit.sound");
					String v = plugin.getConfig().getString("Sounds.recieved-kit.volume");
					String pi = plugin.getConfig().getString("Sounds.recieved-kit.pitch");
					if (isSound(s)) {
						if (isINT(v)) {
							if (isINT(pi)) {
								Sound sound = Sound
										.valueOf(plugin.getConfig().getString("Sounds.enabled-sounds.sound"));
								int volume = plugin.getConfig().getInt("Sounds.enabled-sounds.volume");
								int pitch = plugin.getConfig().getInt("Sounds.enabled-sounds.pitch");
								api.PlaySound(p, sound, volume, pitch);
							}
						}
					}
				}
			}

		} else {
			p.sendMessage(Messages.getMessage("KIT_NOT_EXIST"));
		}
	}
	

	public static String getPermission(String kit) {
		GodKitsApi api = new GodKitsApi();
		if (api.Kitsyml.getString("Kits." + kit + ".permission") != null) {
			return Chat.format(api.Kitsyml.getString("Kits." + kit + ".permission"));
		} else {
			return Chat.format("&cPermission not found.");
		}
	}

	public static int getCooldown(String kit) {
		GodKitsApi api = new GodKitsApi();
		String string = api.Kitsyml.getString("Kits." + kit + ".cooldown");

		if (string != "none") {
			if (isINT(string) == true) {
				int cooldown = api.Kitsyml.getInt("Kits." + kit + ".cooldown");
				return cooldown;
			}
		}
		return 0;

	}
	
	public static String getKitName(String kit) {
		GodKitsApi api = new GodKitsApi();
		if(api.KitsFile.exists()) {
			for(String currentkit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
				if(currentkit.equalsIgnoreCase(kit)) {
					return currentkit;
				}
			}
		}
		return null;
	}
	
	public static String getDisplayname(String kit) {
		GodKitsApi api = new GodKitsApi();
		
		if(api.kitExist(kit)) {
			return Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname"));
		}
		return null;
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

	public static boolean isSound(String message) {
		try {
			Sound.valueOf(message);
			return true;
		} catch (IllegalArgumentException ex) {
			Chat.format("&c" + message + "is not a sound.");
			return false;
		}
	}

}
