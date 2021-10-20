package me.godkits.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.GUI.GUI_KitSettings;
import me.godkits.GUI.GUI_PreviewKit;
import me.godkits.GUI.GUI_SetUpKitGUI;
import me.godkits.GUI.GUI_SetUpKitInventory;
import me.godkits.GUI.GUI_SettingsMenu;
import me.godkits.Messages.Messages;

public class Command_Godkits implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

		Messages api = new Messages();
		GodKitsApi kitsapi = new GodKitsApi();
		if (args.length == 0) {
			if (kitsapi.hasPermission(sender, "godkits.help") == true) {
				sendHelp(sender);
			} else {
				sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName()).replace("%permission%", "godkits.help"));
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("gui")) {
				if (kitsapi.hasPermission(sender, "godkits.gui") == true) {
					if (sender instanceof Player) {
						Player p = (Player) sender;

						GUI_SetUpKitGUI.sendGUI(p);
						p.sendMessage(Messages.getMessage("EDIT_GUI_PUTKITS"));

					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName()).replace("%permission%", "godkits.gui"));
				}
			} else if (args[0].equalsIgnoreCase("settings")) {
				if (kitsapi.hasPermission(sender, "godkits.settings")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						GUI_SettingsMenu.sendGUI(p);
					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName()).replace("%permission%", "godkits.settings"));
				}
			} else if (args[0].equalsIgnoreCase("information") || args[0].equalsIgnoreCase("info")) {
				sendInfo(sender);
			} else {
				if (kitsapi.hasPermission(sender, "godkits.help") == true) {
					sendHelp(sender);
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName()).replace("%permission%", "godkits.help"));
				}
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("create")) {

				if (kitsapi.hasPermission(sender, "godkits.create") == true) {
					if (!kitsapi.kitExist(args[1])) {
						sender.sendMessage(Messages.getMessage("KIT_CREATED").replace("%kit%", args[1])
								.replace("%player%", sender.getName()));
						if (sender instanceof Player) {
							Player p = (Player) sender;
							Kits.createKitPlayer(p, args[1]);
						} else {
							Kits.createKit(args[1]);
						}
					} else {
						sender.sendMessage(Messages.getMessage("KIT_ALREADY_EXIST").replace("%kit%", args[1])
								.replace("%player%", sender.getName()));
					}
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName())
							.replace("%permission%", "godkits.create"));
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (kitsapi.hasPermission(sender, "godkits.remove") == true) {
					String kit = args[1];
					if (kitsapi.kitExist(kit)) {
						Kits.removeKit(Kits.getKitName(kit));
						sender.sendMessage(Messages.getMessage("KIT_REMOVED").replace("%kit%", args[1])
								.replace("%player%", sender.getName()));
					} else {
						sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[1])
								.replace("%player%", sender.getName()));
					}
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName())
							.replace("%permission%", "godkits.remove"));
				}
			} else if (args[0].equalsIgnoreCase("edit")) {

				if (kitsapi.kitExist(args[1])) {
					if (sender instanceof Player) {
						if (kitsapi.hasPermission(sender, "godkits.edit") == true) {
							Player p = (Player) sender;
							GUI_KitSettings.sendGUI(p, Kits.getKitName(args[1]));
						} else {
							sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND")
									.replace("%player%", sender.getName()).replace("%permission%", "godkits.edit"));
						}
					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else {
					sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[1])
							.replace("%player%", sender.getName()));
				}
			
			} else if (args[0].equalsIgnoreCase("setinventory") || args[0].equalsIgnoreCase("setinv")) {
				if (kitsapi.kitExist(args[1])) {
					if (sender instanceof Player) {
						if (kitsapi.hasPermission(sender, "godkits.setinventory") == true) {
							Player p = (Player) sender;
							GUI_SetUpKitInventory.sendEditorGUI(p, Kits.getKitName(args[1]));
						} else {
							sender.sendMessage(
									Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName())
											.replace("%permission%", "godkits.setinventory"));
						}
					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else {
					sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[1])
							.replace("%player%", sender.getName()));
				}
			} else if (args[0].equalsIgnoreCase("preview") || args[0].equalsIgnoreCase("pre")) {
				if (kitsapi.kitExist(args[1])) {
					if (sender instanceof Player) {
						Player p = (Player) sender;

						GUI_PreviewKit.sendKitGUI(p, Kits.getKitName(args[1]));
					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else {
					sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[1])
							.replace("%player%", sender.getName()));
				}
			} else {
				sendHelp(sender);
			}
		} else if(args.length == 3) {
		if(args[0].equalsIgnoreCase("sendpackage") || args[0].equalsIgnoreCase("givepackage") || args[0].equalsIgnoreCase("sendkitpackage") || args[0].equalsIgnoreCase("givekitpackage")) {
			if (kitsapi.hasPermission(sender, "godkits.sendpackage") == true) {
			if(kitsapi.kitExist(args[1])) {
				Player t = Bukkit.getPlayer(args[2]);
				if(t != null) {
					kitsapi.sendKitPackage(t, Kits.getKitName(args[1]));
					sender.sendMessage(Messages.getMessage("SEND_PACKAGE").replace("%kit%", Kits.getDisplayname(Kits.getKitName(args[1]))).replace("%player%", t.getName()));
					t.sendMessage(Messages.getMessage("RECIEVED_PACKAGE").replace("%player%", sender.getName()).replace("%kit%", Kits.getDisplayname(Kits.getKitName(args[1]))));
				} else {
					sender.sendMessage(Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", args[2]));
				}
			} else {
				sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[1]).replace("%player%", sender.getName()));
			}
			} else {
				Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName()).replace("%permission%", "godkits.sendpackage");
			}
		} else {
			sendHelp(sender);
		}
		} else {
			sendHelp(sender);
		}

		return false;
	}

	public void sendHelp(CommandSender sender) {
		sender.sendMessage(Chat.format("&8&m------------------------------------------"));
		sender.sendMessage(Chat.format("         &6&lGodKits Help     "));
		sender.sendMessage(Chat.format("  &c/godkit create [kit]&7: create a kit."));
		sender.sendMessage(Chat.format("  &c/godkit remove [kit]&7: remove a kit."));
		sender.sendMessage(Chat.format("  &c/godkit edit [kit]&7: edit a kit in the gui."));
		sender.sendMessage(Chat.format("  &c/godkit setinventory [kit]&7: set the kit inventory."));
		sender.sendMessage(Chat.format("  &c/godkit preview [kit]&7: show the kit inventory."));
		sender.sendMessage(Chat.format("  &c/godkit settings&7: edit the plugin."));
		sender.sendMessage(Chat.format("  &c/godkit gui&7: edit the kit gui."));
		sender.sendMessage(Chat.format("  &c/godkit info &7: show the plugin info."));
		sender.sendMessage(Chat.format("  &c/godkit sendpackage [kit] [player]"));
		sender.sendMessage(Chat.format("  &c/kit [kit]&7: get a kit."));
		sender.sendMessage(Chat.format("  &c/kits &7: open the kit gui."));
		sender.sendMessage(Chat.format("  &c/sendkit [kit]&7: send a kit to a player."));
		sender.sendMessage(Chat.format("&8&m------------------------------------------"));
	}

	public void sendInfo(CommandSender sender) {
		Main plugin = JavaPlugin.getPlugin(Main.class);

		sender.sendMessage(Chat.format("&8&m------------------------------------------"));
		sender.sendMessage(Chat.format("         &e&lGodKits Information     "));
		sender.sendMessage(Chat.format(""));
		sender.sendMessage(Chat.format("  &aName&7: " + plugin.getName()));
		sender.sendMessage(Chat.format("  &aVersion&7: " + plugin.getDescription().getVersion()));
		sender.sendMessage(Chat.format("  &aAuthor&7: Snapje"));
		sender.sendMessage(Chat.format("  &aWebsite&7: " + plugin.getDescription().getWebsite()));
		sender.sendMessage(Chat.format("  &aDescription&7: " + plugin.getDescription().getDescription()));
		sender.sendMessage(Chat.format("&8&m------------------------------------------"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {

		if (cmd.getName().equalsIgnoreCase("godkits") || cmd.getName().equalsIgnoreCase("gkit")
				|| cmd.getName().equalsIgnoreCase("gkits") || cmd.getName().equalsIgnoreCase("godkit")) {
			ArrayList<String> options = new ArrayList<>();
			GodKitsApi api = new GodKitsApi();
			options.add("create");
			options.add("remove");
			options.add("edit");
			options.add("settings");
			options.add("gui");
			options.add("sendpackage");
			options.add("setinventory");
			options.add("preview");
			options.add("information");

			List<String> list = new ArrayList<>();

			if (args.length == 1) {
				if (api.hasPermission(sender, "godkits.help")) {
					for (String option : options) {
						if (option.toLowerCase().startsWith(args[0].toLowerCase())) {
							list.add(option);
						}
					}
					return list;
				}
			} 
		}
		return null;
	}

}
