package me.godkits.Commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.godkits.API.GodKitsApi;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class Command_PlaySound implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		GodKitsApi api = new GodKitsApi();

		if (cmd.getName().equalsIgnoreCase("playsound")) {
			if (api.hasPermission(sender, "bukkit.command.playsound") || cmd.getName().equalsIgnoreCase("ps")) {
				if (args.length == 1) {
					if (sender instanceof Player) {
						Player p = (Player) sender;

						if (isSound(args[0]) == true) {
							if (!GodKitsApi.disabledsounds.contains(p.getName())) {
								Sound sound = Sound.valueOf(args[0]);
								api.PlaySound(p, sound, 1, 1);
								p.sendMessage(Messages.getMessage("PLAYED_SOUND").replace("%sound", sound.toString())
										.replace("%player%", p.getName()));
							} else {
								sender.sendMessage(Messages.getMessage("HAVE_TO_ENABLE_SOUNDS"));
							}
						} else {
							sender.sendMessage(Messages.getMessage("IS_NOT_A_SOUND").replace("%sound%", args[0]));
						}

					} else {
						sender.sendMessage(Messages.getMessage("CONSOLE"));
					}
				} else if (args.length == 2) {
					Player t = Bukkit.getPlayer(args[1]);
					if (t != null) {

						if (isSound(args[0]) == true) {
							Sound sound = Sound.valueOf(args[0]);
							api.PlaySound(t, sound, 1, 1);
							sender.sendMessage(Messages.getMessage("PLAYED_SOUND").replace("%sound", sound.toString())
									.replace("%player%", t.getName()));
						} else {
							sender.sendMessage(Messages.getMessage("IS_NOT_A_SOUND"));
						}
					} else {
						sender.sendMessage(Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", args[1]));
					}

				} else if (args.length == 3) {
					Player t = Bukkit.getPlayer(args[1]);
					if (isINT(args[2])) {
						if (t != null) {
							if (isSound(args[0]) == true) {
								int volume = Integer.parseInt(args[2]);
								Sound sound = Sound.valueOf(args[0]);
								api.PlaySound(t, sound, volume, 1);
								sender.sendMessage(Messages.getMessage("PLAYED_SOUND")
										.replace("%sound", sound.toString()).replace("%player%", t.getName()));
							} else {
								sender.sendMessage(Messages.getMessage("IS_NOT_A_SOUND").replace("%sound%", args[0]));
							}
						} else {
							sender.sendMessage(Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", args[1]));
						}
					} else {
						sender.sendMessage(Chat.format("&c" + args[2] + " is not a number."));
					}
				} else if (args.length == 4) {
					Player t = Bukkit.getPlayer(args[1]);
					if (isINT(args[2])) {
						if (isINT(args[3])) {
							if (t != null) {
								if (isSound(args[0]) == true) {
									int volume = Integer.parseInt(args[2]);
									int pitch = Integer.parseInt(args[3]);
									Sound sound = Sound.valueOf(args[0]);
									api.PlaySound(t, sound, volume, pitch);
									sender.sendMessage(Messages.getMessage("PLAYED_SOUND")
											.replace("%sound", sound.toString()).replace("%player%", t.getName()));
								} else {
									sender.sendMessage(
											Messages.getMessage("IS_NOT_A_SOUND").replace("%sound%", args[0]));
								}
							} else {
								sender.sendMessage(
										Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", args[1]));
							}
						} else {
							sender.sendMessage(Chat.format("&c" + args[3] + " is not a number."));
						}
					} else {
						sender.sendMessage(Chat.format("&c" + args[2] + " is not a number."));
					}
				} else {
					sender.sendMessage(Chat.format("&c/playsound [sound] [player] [volume] [pitch]"));
				}
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
		GodKitsApi api = new GodKitsApi();
		ArrayList<String> sounds = new ArrayList<>();
		ArrayList<String> players = new ArrayList<>();

		if (cmd.getName().equalsIgnoreCase("playsound") || cmd.getName().equalsIgnoreCase("ps")) {
			if (api.hasPermission(sender, "bukkit.command.playsound")) {
				if (args.length == 1) {
					for (Sound currentsound : Sound.values()) {
						if (currentsound.toString().toLowerCase().startsWith(args[0].toLowerCase())) {
							sounds.add(currentsound.toString());
						}
					}
					Collections.sort(sounds);
					return sounds;
				}
			} else if (args.length >= 2) {
				return null;
			}
		}
		return null;
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
