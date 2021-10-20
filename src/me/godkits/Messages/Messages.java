package me.godkits.Messages;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;

public class Messages {
	
	public static String getMessage(String message) {
		Main plugin = Main.getPlugin(Main.class);
	 File MessagesFile = new File("" + plugin.getDataFolder() + "//Messages.yml");
	 YamlConfiguration Messagesyml = YamlConfiguration.loadConfiguration(MessagesFile);

		GodKitsApi api = new GodKitsApi();
					if (MessagesFile.exists() && api.Messagesyml.getConfigurationSection("Messages") != null && Messagesyml.getString("Messages." + message) != null) {
						return Chat.format(api.Messagesyml.getString("Messages." + message)).replace("%prefix%",api.getPrefix());
					} else {
						if (message.equalsIgnoreCase("ADDED_ICON_LORE")) {
							return Messages.ADDED_ICON_LORE;
						} else if (message.equalsIgnoreCase("CONSOLE")) {
							return Messages.CONSOLE;
						} else if (message.equalsIgnoreCase("CONTACT_STAFF")) {
							return Messages.CONTACT_STAFF;
						} else if (message.equalsIgnoreCase("DISABLED_PLUGIN")) {
							return Messages.DISABLED_PLUGIN;
						} else if (message.equalsIgnoreCase("EDIT_GUI_PUTKITS")) {
							return Messages.EDIT_GUI_PUTKITS;
						} else if (message.equalsIgnoreCase("EDITED_KIT")) {
							return Messages.EDITED_KIT;
						} else if (message.equalsIgnoreCase("ENABLED_PLUGIN")) {
							return Messages.ENABLED_PLUGIN;
						} else if (message.equalsIgnoreCase("ERROR_GUI_SIZE")) {
							return Messages.ERROR_GUI_SIZE;
						} else if (message.equalsIgnoreCase("INVENTORY_FULL")) {
							return Messages.INVENTORY_FULL;
						} else if (message.equalsIgnoreCase("KIT_ALREADY_EXIST")) {
							return Messages.KIT_ALREADY_EXIST;
						} else if (message.equalsIgnoreCase("KIT_COMMAND_USAGE")) {
							return Messages.KIT_COMMAND_USAGE;
						} else if (message.equalsIgnoreCase("KIT_COMMAND_USAGE_TARGET")) {
							return Messages.KIT_COMMAND_USAGE_TARGET;
						} else if (message.equalsIgnoreCase("KIT_CREATED")) {
							return Messages.KIT_CREATED;
						} else if (message.equalsIgnoreCase("KIT_LIST")) {
							return Messages.KIT_LIST;
						} else if (message.equalsIgnoreCase(KIT_NOT_EXIST)) {
							return Messages.KIT_NOT_EXIST;
						} else if (message.equalsIgnoreCase("KIT_REMOVED")) {
							return Messages.KIT_REMOVED;
						} else if (message.equalsIgnoreCase("KIT_SETGUI")) {
							return Messages.KIT_SETGUI;
						} else if (message.equalsIgnoreCase("NO_ITEMS")) {
							return Messages.NO_ITEMS;
						} else if (message.equalsIgnoreCase("NOPERMISSION_COMMAND")) {
							return Messages.NOPERMISSION_COMMAND;
						} else if (message.equalsIgnoreCase("NOPERMISSION_KIT")) {
							return Messages.NOPERMISSION_KIT;
						} else if (message.equalsIgnoreCase("PLAYER_NOT_ONLINE")) {
							return Messages.PLAYER_NOT_ONLINE;
						} else if (message.equalsIgnoreCase("RECIEVED_KIT")) {
							return Messages.RECIEVED_KIT;
						} else if (message.equalsIgnoreCase("REMOVED_ICON_LORE")) {
							return Messages.REMOVED_ICON_LORE;
						} else if (message.equalsIgnoreCase("SEND_KIT")) {
							return Messages.SEND_KIT;
						} else if (message.equalsIgnoreCase("SET_ICON_DISPLAYNAME")) {
							return Messages.SET_ICON_DISPLAYNAME;
						} else if (message.equalsIgnoreCase("SET_KIT_COOLDOWN")) {
							return Messages.SET_KIT_COOLDOWN;
						} else if (message.equalsIgnoreCase("SET_KIT_PERMISSION")) {
							return Messages.SET_KIT_PERMISSION;
						} else if (message.equalsIgnoreCase("TYPE_COOLDOWN")) {
							return Messages.TYPE_COOLDOWN;
						} else if (message.equalsIgnoreCase("TYPE_DISPLAYNAME")) {
							return Messages.TYPE_DISPLAYNAME;
						} else if (message.equalsIgnoreCase("TYPE_LORE_TO_ADD")) {
							return Messages.TYPE_LORE_TO_ADD;
						} else if (message.equalsIgnoreCase("TYPE_LORE_TO_REMOVE")) {
							return Messages.TYPE_LORE_TO_REMOVE;
						} else if (message.equalsIgnoreCase("TYPE_PERMISSION")) {
							return Messages.TYPE_PERMISSION;
						} else if (message.equalsIgnoreCase("TYPE_GUI_NAME")) {
							return Messages.TYPE_GUI_NAME;
						} else if (message.equalsIgnoreCase("SET_GUI_NAME")) {
							return Messages.SET_GUI_NAME;
						} else if (message.equalsIgnoreCase("SET_AMOUNT_OF_ROWS")) {
							return Messages.SET_AMOUNT_OF_ROWS;
						} else if (message.equalsIgnoreCase("TYPE_PREFIX")) {
							return Messages.TYPE_PREFIX;
						} else if (message.equalsIgnoreCase("SET_PREFIX")) {
							return Messages.SET_PREFIX;
						} else if (message.equalsIgnoreCase("SET_DEFAULT_KIT")) {
							return Messages.SET_DEFAULT_KIT;
						} else if (message.equalsIgnoreCase("ENABLED_GUI")) {
							return Messages.ENABLED_GUI;
						} else if (message.equalsIgnoreCase("DISABLED_GUI")) {
							return Messages.DISABLED_GUI;
						} else if (message.equalsIgnoreCase("TYPE_MESSAGE")) {
							return Messages.TYPE_MESSAGE;
						} else if (message.equalsIgnoreCase("SET_MESSAGE")) {
							return Messages.SET_MESSAGE;
						} else if (message.equalsIgnoreCase("ON_KIT_COOLDOWN")) {
							return Messages.ON_KIT_COOLDOWN;
						} else if (message.equalsIgnoreCase("GUI_NAME_NOT_FOUND")) {
							return GUI_NAME_NOT_FOUND;
						} else if (message.equalsIgnoreCase("RESET_KIT")) {
							return RESET_KIT;
						} else if (message.equalsIgnoreCase("IS_NOT_A_SOUND")) {
							return NOT_A_SOUND;
						} else if (message.equalsIgnoreCase("PLAYED_SOUND")) {
							return PLAYED_SOUND;
						} else if (message.equalsIgnoreCase("ENABLED_SOUNDS")) {
							return ENABLED_SOUNDS;
						} else if (message.equalsIgnoreCase("DISABLED_SOUNDS")) {
							return DISABLED_SOUNDS;
						} else if (message.equalsIgnoreCase("HAVE_TO_ENABLE_SOUNDS")) {
							return HAVE_TO_ENABLE_SOUNDS;
						} else if (message.equalsIgnoreCase("TYPE_TIME_FORMAT")) {
							return TYPE_TIME_FORMAT;
						} else if (message.equalsIgnoreCase("SET_TIME_FORMAT")) {
							return SET_TIME_FORMAT;
						} else if (message.equalsIgnoreCase("CANCEL")) {
							return CANCEL;
						} else if (message.equalsIgnoreCase("CANCELED_ACTION")) {
							return CANCELED_ACTION;
						} else if (message.equalsIgnoreCase("NOT_A_SOUND")) {
							return NOT_A_SOUND;
						} else if (message.equalsIgnoreCase("KIT_SETINVENTORY")) {
							return KIT_SETINVENTORY;
						} else if(message.equalsIgnoreCase("SEND_PACKAGE")) {	
							return SEND_PACKAGE;
						} else if(message.equalsIgnoreCase("RECIEVED_PACKAGE")) {
							return RECIEVED_PACKAGE;
						} else if(message.equalsIgnoreCase("OPENED_PACKAGE")) {
							return OPENED_PACKAGE;
						} else {
							return Chat.format("&cThis message does not exist");
						}
					}
	}

	static GodKitsApi api = new GodKitsApi();
	public static String ENABLED_PLUGIN = Chat.format("%prefix% &7Turned the godkits plugin &aON&7.").replace("%prefix%", api.getPrefix());
	public static String DISABLED_PLUGIN = Chat.format("%prefix% &7Turned the godkits plugin &cOFF&7.").replace("%prefix%", api.getPrefix());
	public static String KIT_CREATED = Chat.format("%prefix% &e%player% &7created the kit &e'%kit%'&7.").replace("%prefix%", api.getPrefix());
	public static String KIT_REMOVED = Chat.format("%prefix% &e%player% &7removed the kit &e'%kit%' &7from the config.").replace("%prefix%", api.getPrefix());
	public static String KIT_SETINVENTORY = Chat.format("%prefix% &7You set the kit items.").replace("%prefix%",api.getPrefix());
	public static String KIT_LIST = Chat.format("%prefix% &6&lKits&8: &7%kits%&7.").replace("%prefix%",api.getPrefix());
	public static String NOPERMISSION_KIT = Chat.format("&cYou do not have permission for this kit.").replace("%prefix%", api.getPrefix());
	public static String NOPERMISSION_COMMAND = Chat.format("&cYou do not have permission to execute this command.").replace("%prefix%", api.getPrefix());
	public static String CONSOLE = Chat.format("&cOnly players can use this command.").replace("%prefix%",api.getPrefix());
	public static String RECIEVED_KIT = Chat.format("&e%player% &7recieved the kit &e'%kit%'&7.").replace("%prefix%",api.getPrefix());
	public static String SEND_KIT = Chat.format("&e%player% &7recieved the kit &e'%kit%'&7.").replace("%prefix%",api.getPrefix());
	public static String NO_ITEMS = Chat.format("&cThis kit does not contain any items.").replace("%prefix%",api.getPrefix());
	public static String INVENTORY_FULL = Chat.format("&cYour inventory is full so we dropped the items on the ground.").replace("%prefix%", api.getPrefix());
	public static String KIT_ALREADY_EXIST = Chat.format("&cThis kit does already exist.").replace("%prefix%",api.getPrefix());
	public static String KIT_NOT_EXIST = Chat.format("&cThis kit does not exist.").replace("%prefix%", api.getPrefix());
	public static String EDITED_KIT = Chat.format("%prefix% &7You edited the kit &e'%kit%&7.").replace("%prefix%",api.getPrefix());
	public static String EDIT_GUI_PUTKITS = Chat.format("%prefix% &7Keep the slots where the kits have to come open.").replace("%prefix%", api.getPrefix());
	public static String TYPE_PERMISSION = Chat.format("&7Type the permission you want.").replace("%prefix%",api.getPrefix());
	public static String TYPE_COOLDOWN = Chat.format("&7Type the cooldown in seconds..").replace("%prefix%",api.getPrefix());
	public static String SET_KIT_PERMISSION = Chat.format("%prefix% &7You set the permission of &e'%kit%' &7to &e%permission%&7.").replace("%prefix%", api.getPrefix());
	public static String SET_KIT_COOLDOWN = Chat.format("%prefix% &7You set the cooldown of &e'%kit%' &7to &e%cooldown%&7 seconds.").replace("%prefix%", api.getPrefix());
	public static String ON_KIT_COOLDOWN = Chat.format("&cYoure still on cooldown for %cooldown%.").replace("%prefix%",api.getPrefix());
	public static String TYPE_LORE_TO_ADD = Chat.format("&7Type the lore you want to add.").replace("%prefix%",api.getPrefix());
	public static String TYPE_LORE_TO_REMOVE = Chat.format("&7Type the lore you want to remove.").replace("%prefix%",api.getPrefix());
	public static String TYPE_DISPLAYNAME = Chat.format("&7Type the new displayname for the icon.").replace("%prefix%",api.getPrefix());
	public static String ADDED_ICON_LORE = Chat.format("%prefix% &7You added &e'%lore%&e' &7to the icon lore.").replace("%prefix%", api.getPrefix());
	public static String REMOVED_ICON_LORE = Chat.format("%prefix% &7You removed &e'%lore%&e' &7from the icon lore.").replace("%prefix%", api.getPrefix());
	public static String SET_ICON_DISPLAYNAME = Chat.format("%prefix% &7You set the displayname of &e'%kit%' &7to &e'%displayname%&e'&7.").replace("%prefix%", api.getPrefix());
	public static String ERROR_GUI_SIZE = Chat.format("&cThe kit menu is to small you need to increase the amount of rows in the config.").replace("%prefix%", api.getPrefix());
	public static String CONTACT_STAFF = Chat.format("&cThere is a problem with the plugin please contact staff.").replace("%prefix%", api.getPrefix());
	public static String PLAYER_NOT_ONLINE = Chat.format("&cThis player is not online.").replace("%prefix%",api.getPrefix());
	public static String KIT_COMMAND_USAGE = Chat.format("&cUse&7: /kit [kitname].").replace("%prefix%",api.getPrefix());
	public static String KIT_COMMAND_USAGE_TARGET = Chat.format("&cUse&7: /sendkit [kitname] [player].").replace("%prefix%", api.getPrefix());
	public static String KIT_SETGUI = Chat.format("%prefix% &7You changed the kit gui.").replace("%prefix%",api.getPrefix());
	public static String TYPE_GUI_NAME = Chat.format("&7Type the new gui name.").replace("%prefix%", api.getPrefix());
	public static String SET_AMOUNT_OF_ROWS = Chat.format("%prefix% &7You set the amount of rows to &e%rows%&7.").replace("%prefix%", api.getPrefix());
	public static String SET_GUI_NAME = Chat.format("%prefix% &7You set the gui name of &e%gui% &7to %name%&7.").replace("%prefix%", api.getPrefix());
	public static String TYPE_PREFIX = Chat.format("&7Type the new plugin prefix.").replace("%prefix%",api.getPrefix());
	public static String SET_PREFIX = Chat.format("%prefix% &7You the the plugin prefix to %newprefix% &7.").replace("%prefix%", api.getPrefix());
	public static String SET_DEFAULT_KIT = Chat.format("%prefix% &7You set the default kit to &e%kit%&7.").replace("%prefix%", api.getPrefix());
	public static String ENABLED_GUI = Chat.format("%prefix% &7You &aenabled &7the kit gui.").replace("%prefix%",api.getPrefix());
	public static String DISABLED_GUI = Chat.format("%prefix% &7You &cdisabled &7the kit gui.").replace("%prefix%",api.getPrefix());
	public static String TYPE_MESSAGE = Chat.format("&7Type the new message.").replace("%prefix%", api.getPrefix());
	public static String SET_MESSAGE = Chat.format("%prefix% &7You chanced the message from &e%message% &7to %newmessage%&7.").replace("%prefix%", api.getPrefix());
	public static String GUI_NAME_NOT_FOUND = Chat.format("&cCould not find the name of %gui%.").replace("%prefix%",api.getPrefix());
	public static String RESET_KIT = Chat.format("&cError with %kit% please recreate the kit.").replace("%prefix%",api.getPrefix());
	public static String PLAYED_SOUND = Chat.format("%prefix% &7You played a sound on &e%player%'s &7location.").replace("%prefix%", api.getPrefix());
	public static String ENABLED_SOUNDS = Chat.format("%prefix% &7You turned plugin sounds &aON&7.").replace("%prefix%",api.getPrefix());
	public static String DISABLED_SOUNDS = Chat.format("%prefix% &7You turned plugin sounds &cOFF&7.").replace("%prefix%", api.getPrefix());
	public static String HAVE_TO_ENABLE_SOUNDS = Chat.format("&cYou have to enable sounds to hear this sound.").replace("%prefix%", api.getPrefix());
	public static String TYPE_TIME_FORMAT = Chat.format("&7Type the new time format. Use: %day%, %hours%, %minutes%, %seconds%.").replace("%prefix%", api.getPrefix());
	public static String SET_TIME_FORMAT = Chat.format("%prefix% &7You set the new time format to &e'%newTimeFormat%'&7.").replace("%prefix%", api.getPrefix());
	public static String CANCEL = Chat.format("&7Type &c&lcancel &7to cancel this action.").replace("%prefix%",api.getPrefix());
	public static String CANCELED_ACTION = Chat.format("&7Succesfully &c&lcanceled &7the action.").replace("%prefix%",api.getPrefix());
	public static String NOT_A_SOUND = Chat.format("&cContact a staffmember to fix the sounds in the config.").replace("%prefix%", api.getPrefix());
	public static String SEND_PACKAGE = Chat.format("%prefix% &7You send a %kit% package to &e%player%.").replace("%prefix%", api.getPrefix());
	public static String RECIEVED_PACKAGE = Chat.format("%prefix% &7You recieved a %kit% &7package (Right-Click) to open.").replace("%prefix%", api.getPrefix());
	public static String OPENED_PACKAGE = Chat.format("%prefix% &7You have used your %kit% &7package.").replace("%prefix%", api.getPrefix());
}
