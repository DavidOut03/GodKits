package me.godkits.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GodKitsApi {

	public static ArrayList<String> disabledsounds = new ArrayList<>();

	// Kits fileconfiguratio
	public Main plugin = Main.getPlugin(Main.class);
	public File KitsFile = new File("" + plugin.getDataFolder() + "//Kits.yml");
	public YamlConfiguration Kitsyml = YamlConfiguration.loadConfiguration(KitsFile);

	// Messages fileconfiguration
	public File MessagesFile = new File("" + plugin.getDataFolder() + "//Messages.yml");
	public YamlConfiguration Messagesyml = YamlConfiguration.loadConfiguration(MessagesFile);

	// kitsgui configuration
	public File KitsGUIFile = new File("" + plugin.getDataFolder() + "//GUI//KitsGUI.yml");
	public YamlConfiguration KitsGUIyml = YamlConfiguration.loadConfiguration(KitsGUIFile);

	// cooldown
	public File CooldownFile = new File("" + plugin.getDataFolder() + "//database//Cooldowns.yml");
	public YamlConfiguration Cooldownyml = YamlConfiguration.loadConfiguration(CooldownFile);

	// folders
	public File datafolder = new File("" + plugin.getDataFolder());
	public File GUI = new File("" + plugin.getDataFolder() + "//GUI");
	public File database = new File("" + plugin.getDataFolder() + "//database");

	public void getFolders() {
		// folders
		File datafolder = new File("" + plugin.getDataFolder());
		File GUI = new File("" + plugin.getDataFolder() + "//GUI");
		File database = new File("" + plugin.getDataFolder() + "//database");

		if (datafolder.exists()) {
			datafolder.mkdirs();
		}
		if (!GUI.exists()) {
			GUI.mkdirs();
		}
		if (!database.exists()) {
			database.mkdirs();
		}
	}

	public void getFiles() {
		Bukkit.getConsoleSender().sendMessage(Chat.format("&8&m-------------------------------"));
		if (!KitsGUIFile.exists()) {
			try {
				ArrayList<ItemStack> items = new ArrayList<>();
				items.add(new ItemStack(Material.AIR));
				KitsGUIyml.set("GUI.content", items);
				KitsGUIyml.save(KitsGUIFile);
				Bukkit.getConsoleSender().sendMessage(Chat.format(getPrefix() + " &7Created &6GUI &7file."));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
			plugin.saveDefaultConfig();
		}
		if (!KitsFile.exists()) {
			try {
				KitsFile.createNewFile();
				Kitsyml.createSection("Kits");
				Kitsyml.save(KitsFile);
				Bukkit.getConsoleSender().sendMessage(Chat.format(getPrefix() + " &7Created &aConfig.yml &7file."));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			Kits.createKit("default");
		}
		if (!MessagesFile.exists()) {
			try {
				// plugin status
				Messagesyml.set("Messages.ENABLED_PLUGIN", "%prefix% &7Turned the godkits plugin &aON&7.");
				Messagesyml.set("Messages.DISABLED_PLUGIN", "%prefix% &7Turned the godkits plugin &cOFF&7.");
				Messagesyml.set("Messages.SET_PREFIX", "%prefix% &7You the the plugin prefix to %newprefix% &7.");
				Messagesyml.set("Messages.SET_MESSAGE",
						"%prefix% &7You chanced the message from &e%message% &7to %newmessage%&7.");

				// op player kit messages
				Messagesyml.set("Messages.KIT_CREATED", "%prefix% &e%player% &7created the kit &e'%kit%'&7.");
				Messagesyml.set("Messages.KIT_ALREADY_EXIST", "&cThis kit does already exist.");
				Messagesyml.set("Messages.KIT_REMOVED",
						"%prefix% &e%player% &7removed the kit &e'%kit%' &7from the config.");
				Messagesyml.set("Messages.KIT_NOT_EXIST", "&cThis kit does not exist.");
				Messagesyml.set("Messages.SEND_KIT", "&e%player% &7recieved the kit &e'%kit%'&7.");
				Messagesyml.set("Messages.EDITED_KIT", "%prefix% &7You edited the kit &e'%kit%&7.");
				Messagesyml.set("Messages.KIT_SETINVENTORY", "%prefix% &7You set the kit items.");
				Messagesyml.set("Messages.KIT_SETGUI", "%prefix% &7You changed the kit gui.");
				Messagesyml.set("Messages.EDIT_GUI_PUTKITS",
						"%prefix% &7Keep the slots where the kits have to come open.");
				Messagesyml.set("Messages.SET_KIT_PERMISSION",
						"%prefix% &7You set the permission of &e'%kit%' &7to &e%permission%&7.");
				Messagesyml.set("Messages.SET_KIT_COOLDOWN",
						"%prefix% &7You set the cooldown of &e'%kit%' &7to &e%cooldown%&7 seconds.");
				Messagesyml.set("Messages.ADDED_ICON_LORE", "%prefix% &7You added &e'%lore%&e' &7to the icon lore.");
				Messagesyml.set("Messages.REMOVED_ICON_LORE",
						"%prefix% &7You removed &e'%lore%&e' &7from the icon lore.");
				Messagesyml.set("Messages.SET_ICON_DISPLAYNAME",
						"%prefix% &7You set the displayname of &e'%kit%' &7to &e'%displayname%&e'&7.");
				Messagesyml.set("Messages.SET_DEFAULT_KIT", "%prefix% &7You set the default kit to &e%kit%&7.");
				Messagesyml.set("Messages.SET_TIME_FORMAT",
						"%prefix% &7You set the new time format to &e'%newTimeFormat%'&7.");

				// gui messages:
				Messagesyml.set("Messages.ENABLED_GUI", "%prefix% &7You &aenabled &7the kit gui.");
				Messagesyml.set("Messages.DISABLED_GUI", "%prefix% &7You &cdisabled &7the kit gui.");
				Messagesyml.set("Messages.SET_GUI_NAME", "%prefix% &7You set the gui name of &e%gui% &7 to %name%&7.");
				Messagesyml.set("Messages.SET_AMOUNT_OF_ROWS", "%prefix% &7You set the amount of rows to &e%rows%&7.");

				// type messages
				Messagesyml.set("Messages.TYPE_PERMISSION", "&7Type the permission you want.");
				Messagesyml.set("Messages.TYPE_COOLDOWN", "&7Type the cooldown in seconds..");
				Messagesyml.set("Messages.TYPE_LORE_TO_ADD", "&7Type the lore you want to add.");
				Messagesyml.set("Messages.TYPE_LORE_TO_REMOVE", "&7Type the lore you want to remove.");
				Messagesyml.set("Messages.TYPE_DISPLAYNAME", "&7Type the new displayname for the icon.");
				Messagesyml.set("Messages.TYPE_GUI_NAME", "&7Type the new gui name.");
				Messagesyml.set("Messages.TYPE_PREFIX", "&7Type the new plugin prefix.");
				Messagesyml.set("Messages.TYPE_MESSAGE", "&7Type the new message.");
				Messagesyml.set("Messages.TYPE_TIME_FORMAT",
						"&7Type the new time format. Use: %day%, %hours%, %minutes%, %seconds%.");
				Messagesyml.set("Messages.CANCEL", "&7Type &c&lcancel &7to cancel this action.");
				Messagesyml.set("Messages.CANCELED_ACTION", "&7Succesfully &c&lcanceled &7the action.");

				// regular player kit messages
				Messagesyml.set("Messages.KIT_LIST", "%prefix% &6&lKits&8: &7%kits%&7.");
				Messagesyml.set("Messages.RECIEVED_KIT", "&7You received the kit &e'%kit%'&7.");
				Messagesyml.set("Messages.ON_KIT_COOLDOWN", "&cYoure still on cooldown for %cooldown%.");
				Messagesyml.set("Messages.INVENTORY_FULL",
						"&cYour inventory is full so we dropped the items on the ground.");

				// usages:
				Messagesyml.set("Messages.KIT_COMMAND_USAGE", "&cUse&7: /kit [kitname].");
				Messagesyml.set("Messages.KIT_COMMAND_USAGE_TARGET", "&cUse&7: /sendkit [kitname] [player].");

				// nopermission
				Messagesyml.set("Messages.NOPERMISSION_KIT", "&cYou do not have permission for this kit.");
				Messagesyml.set("Messages.NOPERMISSION_COMMAND",
						"&cYou do not have permission to execute this command.");

				// erros
				Messagesyml.set("Messages.CONSOLE", "&cOnly players can use this command.");
				Messagesyml.set("Messages.NO_ITEMS", "&cThis kit does not contain any items.");
				Messagesyml.set("Messages.ERROR_GUI_SIZE",
						"&cThe kit menu is to small you need to increase the amount of rows in the config.");
				Messagesyml.set("Messages.CONTACT_STAFF", "&cThere is a problem with the plugin please contact staff.");
				Messagesyml.set("Messages.PLAYER_NOT_ONLINE", "&cThis player is not online.");
				Messagesyml.set("Messages.GUI_NAME_NOT_FOUND", "&cCould not find the name of %gui%.");
				Messagesyml.set("Messages.RESET_KIT", "&cError with %kit% please recreate the kit.");
				Messagesyml.set("Messages.HAVE_TO_ENABLE_SOUNDS", "&cYou have to enable sounds to hear this sound.");
				Messagesyml.set("Messages.NOT_A_SOUND", "&cContact a staffmember to fix the sounds in the config.");

				// other command messages
				Messagesyml.set("Messages.PLAYED_SOUND", "%prefix% &7You played a sound on &e%player%'s &7location.");
				Messagesyml.set("Messages.ENABLED_SOUNDS", "%prefix% &7You turned plugin sounds &aON&7.");
				Messagesyml.set("Messages.DISABLED_SOUNDS", "%prefix% &7You turned plugin sounds &cOFF&7.");
				Messagesyml.set("Messages.HAVE_TO_ENABLE_SOUNDS", "&cYou have to enable sounds to hear this sound.");

				Bukkit.getConsoleSender().sendMessage(Chat.format(getPrefix() + " &7Created &dMessages.yml &7file."));
				Messagesyml.save(MessagesFile);
				plugin.reloadConfig();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		if (!CooldownFile.exists()) {
			try {
				CooldownFile.createNewFile();
				Cooldownyml.createSection("Users");
				Cooldownyml.save(CooldownFile);
				Bukkit.getConsoleSender().sendMessage(Chat.format(getPrefix() + " &7Created &3Cooldowns.yml &7file."));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Bukkit.getConsoleSender().sendMessage(Messages.getMessage("ENABLED_PLUGIN"));
		Bukkit.getConsoleSender().sendMessage(Chat.format("&8&m-------------------------------"));
	}
	
	public void sendKitPackage(Player p, String kit) {
		if(kitExist(kit)) {
			ItemStack kitpackage = new ItemStack(Material.CHEST);
			ItemMeta meta = kitpackage.getItemMeta();
			ArrayList<String> packagelore = new ArrayList<String>();
			if(plugin.getConfig().getConfigurationSection("Settings.package-lore") != null) {
			List<String> lore = (List<String>) plugin.getConfig().getConfigurationSection("Settings.package-lore");
			for(String currentlore : lore) {
				packagelore.add(Chat.format(currentlore).replace("%kit%", Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname"))));
			}
			} else {
				packagelore.add(Chat.format("&7(Left-Click) to preview the %kit% &7kit.").replace("%kit%", Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname")) ));
				packagelore.add(Chat.format("&7(Right-Click) to open the %kit% package.").replace("%kit%", Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname")) ));
			}
			meta.setDisplayName(Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
			meta.setLore(packagelore);
			kitpackage.setItemMeta(meta);
			p.getInventory().addItem(kitpackage);
		}
	}
	
	public boolean kitExist(String kit) {
		for(String currentkit : Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
			if(currentkit.equalsIgnoreCase(kit)) {
			return true;
			}
		}
			return false;
	}

	public String getPrefix() {

		if (plugin.getConfig().getString("Settings.prefix") != null) {
			String prefix = plugin.getConfig().getString("Settings.prefix");
			return Chat.format(prefix);
		} else {
			return Chat.format("&cPrefix not found.");
		}
	}
	
	public String getKitFromDisplayname(String displayname) {
		for (String kit : Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
			if (Chat.format(Kitsyml.getString("Kits." + kit + ".GUI.displayname")).equalsIgnoreCase(Chat.format(displayname))) {
				return kit;
			}
		}
		return null;
	}

	public void PlaySound(Player p, Sound sound, int volume, int pitch) {
		p.playSound(p.getLocation(), sound, volume, pitch);
	}

	public boolean hasPermission(CommandSender sender, String permission) {
		if (sender.hasPermission("godkits.*") || sender.hasPermission(permission) || sender.isOp()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkInt(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
