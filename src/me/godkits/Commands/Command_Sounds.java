package me.godkits.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class Command_Sounds implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

		if (cmd.getName().equalsIgnoreCase("sounds") || cmd.getName().equalsIgnoreCase("s")) {
			if (sender instanceof Player) {
				GodKitsApi api = new GodKitsApi();
				Main plugin = JavaPlugin.getPlugin(Main.class);
				Player player = (Player) sender;
				if (args.length == 0) {
					if (GodKitsApi.disabledsounds.contains(player.getName())) {

						player.sendMessage(Messages.getMessage("ENABLED_SOUNDS").replace("%player%", player.getName()));
						if (plugin.getConfig().getString("Sounds.enabled-sounds.sound") != null
								&& plugin.getConfig().getString("Sounds.enabled-sounds.volume") != null
								&& plugin.getConfig().getString("Sounds.enabled-sounds.pitch") != null) {
							String s = plugin.getConfig().getString("Sounds.enabled-sounds.sound");
							String v = plugin.getConfig().getString("Sounds.enabled-sounds.volume");
							String pi = plugin.getConfig().getString("Sounds.enabled-sounds.pitch");
							if (isSound(s)) {
								if (isINT(v)) {
									if (isINT(pi)) {
										Sound sound = Sound
												.valueOf(plugin.getConfig().getString("Sounds.enabled-sounds.sound"));
										int volume = plugin.getConfig().getInt("Sounds.enabled-sounds.volume");
										int pitch = plugin.getConfig().getInt("Sounds.enabled-sounds.pitch");
										Player p = (Player) sender;
										api.PlaySound(p, sound, volume, pitch);
									}
								}
							}
						}
						GodKitsApi.disabledsounds.remove(player.getName());
					} else {
						if (plugin.getConfig().getString("Sounds.disabled-sounds.sound") != null
								&& plugin.getConfig().getString("Sounds.disabled-sounds.volume") != null
								&& plugin.getConfig().getString("Sounds.disabled-sounds.pitch") != null) {
							String s = plugin.getConfig().getString("Sounds.disabled-sounds.sound");
							String v = plugin.getConfig().getString("Sounds.disabled-sounds.volume");
							String pi = plugin.getConfig().getString("Sounds.disabled-sounds.pitch");
							if (isSound(s)) {
								if (isINT(v)) {
									if (isINT(pi)) {
										Sound sound = Sound
												.valueOf(plugin.getConfig().getString("Sounds.disabled-sounds.sound"));
										int volume = plugin.getConfig().getInt("Sounds.disabled-sounds.volume");
										int pitch = plugin.getConfig().getInt("Sounds.disabled-sounds.pitch");
										Player p = (Player) sender;
										api.PlaySound(p, sound, volume, pitch);
									}
								}
							}
						}
						GodKitsApi.disabledsounds.add(player.getName());
						player.sendMessage(
								Messages.getMessage("DISABLED_SOUNDS").replace("%player%", player.getName()));

					}
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("on")) {
						GodKitsApi.disabledsounds.remove(player.getName());
						player.sendMessage(Messages.getMessage("ENABLED_SOUNDS").replace("%player%", player.getName()));
						if (plugin.getConfig().getString("Sounds.enabled-sounds.sound") != null
								&& plugin.getConfig().getString("Sounds.enabled-sounds.volume") != null
								&& plugin.getConfig().getString("Sounds.enabled-sounds.pitch") != null) {
							String s = plugin.getConfig().getString("Sounds.enabled-sounds.sound");
							String v = plugin.getConfig().getString("Sounds.enabled-sounds.volume");
							String pi = plugin.getConfig().getString("Sounds.enabled-sounds.pitch");
							if (isSound(s)) {
								if (isINT(v)) {
									if (isINT(pi)) {
										Sound sound = Sound
												.valueOf(plugin.getConfig().getString("Sounds.enabled-sounds.sound"));
										int volume = plugin.getConfig().getInt("Sounds.enabled-sounds.volume");
										int pitch = plugin.getConfig().getInt("Sounds.enabled-sounds.pitch");
										Player p = (Player) sender;
										api.PlaySound(p, sound, volume, pitch);
									}
								}
							}
						}
					} else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
						if (plugin.getConfig().getString("Sounds.disabled-sounds.sound") != null
								&& plugin.getConfig().getString("Sounds.disabled-sounds.volume") != null
								&& plugin.getConfig().getString("Sounds.disabled-sounds.pitch") != null) {
							String s = plugin.getConfig().getString("Sounds.disabled-sounds.sound");
							String v = plugin.getConfig().getString("Sounds.disabled-sounds.volume");
							String pi = plugin.getConfig().getString("Sounds.disabled-sounds.pitch");
							if (isSound(s)) {
								if (isINT(v)) {
									if (isINT(pi)) {
										Sound sound = Sound
												.valueOf(plugin.getConfig().getString("Sounds.disabled-sounds.sound"));
										int volume = plugin.getConfig().getInt("Sounds.disabled-sounds.volume");
										int pitch = plugin.getConfig().getInt("Sounds.disabled-sounds.pitch");
										Player p = (Player) sender;
										api.PlaySound(p, sound, volume, pitch);
									}
								}
							}
						}
						GodKitsApi.disabledsounds.add(player.getName());
						player.sendMessage(
								Messages.getMessage("DISABLED_SOUNDS").replace("%player%", player.getName()));
					} else if (args[0].equalsIgnoreCase("toggle")) {
						if (GodKitsApi.disabledsounds.contains(player.getName())) {
							GodKitsApi.disabledsounds.remove(player.getName());
							if (plugin.getConfig().getString("Sounds.enabled-sounds.sound") != null
									&& plugin.getConfig().getString("Sounds.enabled-sounds.volume") != null
									&& plugin.getConfig().getString("Sounds.enabled-sounds.pitch") != null) {
								String s = plugin.getConfig().getString("Sounds.enabled-sounds.sound");
								String v = plugin.getConfig().getString("Sounds.enabled-sounds.volume");
								String pi = plugin.getConfig().getString("Sounds.enabled-sounds.pitch");
								if (isSound(s)) {
									if (isINT(v)) {
										if (isINT(pi)) {
											Sound sound = Sound.valueOf(
													plugin.getConfig().getString("Sounds.enabled-sounds.sound"));
											int volume = plugin.getConfig().getInt("Sounds.enabled-sounds.volume");
											int pitch = plugin.getConfig().getInt("Sounds.enabled-sounds.pitch");
											Player p = (Player) sender;
											api.PlaySound(p, sound, volume, pitch);
										}
									}
								}
							}
							player.sendMessage(
									Messages.getMessage("ENABLED_SOUNDS").replace("%player%", player.getName()));
						} else if (!GodKitsApi.disabledsounds.contains(player.getName())) {
							GodKitsApi.disabledsounds.add(player.getName());
							if (plugin.getConfig().getString("Sounds.disabled-sounds.sound") != null
									&& plugin.getConfig().getString("Sounds.disabled-sounds.volume") != null
									&& plugin.getConfig().getString("Sounds.disabled-sounds.pitch") != null) {
								String s = plugin.getConfig().getString("Sounds.disabled-sounds.sound");
								String v = plugin.getConfig().getString("Sounds.disabled-sounds.volume");
								String pi = plugin.getConfig().getString("Sounds.disabled-sounds.pitch");
								if (isSound(s)) {
									if (isINT(v)) {
										if (isINT(pi)) {
											Sound sound = Sound.valueOf(
													plugin.getConfig().getString("Sounds.disabled-sounds.sound"));
											int volume = plugin.getConfig().getInt("Sounds.disabled-sounds.volume");
											int pitch = plugin.getConfig().getInt("Sounds.disabled-sounds.pitch");
											Player p = (Player) sender;
											api.PlaySound(p, sound, volume, pitch);
										}
									}
								}
							}
							player.sendMessage(
									Messages.getMessage("DISABLED_SOUNDS").replace("%player%", player.getName()));
						}
					}
				}
			} else {
				sender.sendMessage(Messages.getMessage("CONSOLE"));
			}
		}
		return false;
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

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {

		if (cmd.getName().equalsIgnoreCase("sounds") || cmd.getName().equalsIgnoreCase("s")) {
			List<String> list = new ArrayList<>();

			if (args.length == 1) {
				list.add("toggle");
				list.add("on");
				list.add("off");
				Collections.sort(list);
				return list;
			}

		}
		return null;
	}

}
