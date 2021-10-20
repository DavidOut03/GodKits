package me.godkits.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.godkits.API.CooldownManager;
import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Format.Chat;
import me.godkits.Format.Time;
import me.godkits.Messages.Messages;

public class Command_Kit implements CommandExecutor {

	public static HashMap<Player, String> cooldown = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		GodKitsApi api = new GodKitsApi();

		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					sender.sendMessage(Messages.getMessage("KIT_COMMAND_USAGE"));
				} else if (args.length == 1) {
					
					Player p = (Player) sender;
					if (api.kitExist(args[0])) {
						String kit = Kits.getKitName(args[0]);
						if (api.Kitsyml.getString("Kits." + kit + ".permission") != null && api.Kitsyml.get("Kits." + kit + ".cooldown") != null) {
							if (api.Kitsyml.getString("Kits." + kit + ".permission").equals("none")) {
								if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none") || !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
									if (!api.hasPermission(p, "godkits.bypasscooldown")) {
										if (isINT(api.Kitsyml.getString("Kits." + kit + ".cooldown")) == true) {
											if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
												p.sendMessage(Messages.getMessage("ON_KIT_COOLDOWN").replace("%cooldown%",Time.format(CooldownManager.getRemainingTime(p.getUniqueId(), kit))).replace("%player%", p.getName()).replace("%kit%", kit));
											} else {
												CooldownManager cooldown = new CooldownManager(p.getUniqueId(), kit,api.Kitsyml.getInt("Kits." + kit + ".cooldown"));
												cooldown.start();
												Kits.sendKit(p, kit);
											}
										} else {
											p.sendMessage(Chat.format("&c" + api.Kitsyml
													.getString("Kits." + kit + ".cooldown" + " is not a number.")));
										}
									} else {
										Kits.sendKit(p, kit);
									}
								} else {
									Kits.sendKit(p, kit);
								}
							} else {
								if (api.hasPermission(sender, api.Kitsyml.getString("Kits." + kit + ".permission"))) {
									if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
										if (!api.hasPermission(p, "godkits.bypasscooldown")) {
											if (isINT(api.Kitsyml.getString("Kits." + kit + ".cooldown")) == true) {
												if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
													p.sendMessage(Messages.getMessage("ON_KIT_COOLDOWN").replace("%cooldown%", Time.format(CooldownManager.getRemainingTime(p.getUniqueId(), kit))).replace("%player%", p.getName()).replace("%kit%", kit));
												} else {
													CooldownManager cooldown = new CooldownManager(p.getUniqueId(), kit,api.Kitsyml.getInt("Kits." + kit + ".cooldown"));
													cooldown.start();
													Kits.sendKit(p, kit);
												}
											} else {
												p.sendMessage(Chat.format("&c" + api.Kitsyml.getString("Kits." + kit + ".cooldown" + " is not a number.")));
											}
										} else {
											Kits.sendKit(p, kit);
										}
									} else {
										Kits.sendKit(p, kit);
									}
								} else {
									p.sendMessage(Messages.getMessage("NOPERMISSION_KIT").replace("%kit%", kit).replace("%permission%",api.Kitsyml.getString("Kits." + kit + ".permission")).replace("%player%", p.getName()));
								}
							}
						} else {
							p.sendMessage(Messages.getMessage("RESET_KIT").replace("%kit%", kit));
						}
					} else {
						sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[0]));
					}
				} else if (args.length == 2) {
					if (api.hasPermission(sender, "godkits.sendkit")) {
						if (api.kitExist(args[0])) {
							Player t = Bukkit.getPlayer(args[1]);
							OfflinePlayer to = Bukkit.getOfflinePlayer(args[1]);

							if (t == null) {
								sender.sendMessage(
										Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", to.getName()));
							} else {
								Kits.sendKit(t, Kits.getKitName(args[0]));
								sender.sendMessage(Messages.getMessage("SEND_KIT").replace("%player%", t.getName())
										.replace("%kit%", Kits.getKitName(args[0])));
							}

						} else {
							sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[0]));
						}
					} else {
						if (api.hasPermission(sender, "godkits.sendkit")) {
							sender.sendMessage(Messages.getMessage("KIT_COMMAND_USAGE_TARGET"));
						} else {
							sender.sendMessage(Messages.getMessage("KIT_COMMAND_USAGE"));
						}
					}
				} else {
					sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName())
							.replace("%permission%", "godkits.help"));
				}
			} else {
				if (args.length == 2) {
					if (api.kitExist(args[0])) {
						Player t = Bukkit.getPlayer(args[1]);
						OfflinePlayer to = Bukkit.getOfflinePlayer(args[1]);

						if (t == null) {
							sender.sendMessage(
									Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", to.getName()));
						} else {
							Kits.sendKit(t, Kits.getKitName(args[0]));
							sender.sendMessage(Messages.getMessage("SEND_KIT").replace("%player%", t.getName())
									.replace("%kit%", args[0]));
						}

					} else {
						sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[0]));
					}
				} else {
					sender.sendMessage(Messages.getMessage("KIT_COMMAND_USAGE_TARGET"));
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("sendkit")) {
			if (sender.hasPermission("godkits.sendkit")) {
				if (args.length == 2) {
					if (api.Kitsyml.getConfigurationSection("Kits").contains(args[0])) {
						Player t = Bukkit.getPlayer(args[1]);
						OfflinePlayer to = Bukkit.getOfflinePlayer(args[1]);

						if (t == null) {
							sender.sendMessage(
									Messages.getMessage("PLAYER_NOT_ONLINE").replace("%player%", to.getName()));
						} else {
							Kits.sendKit(t, Kits.getKitName(args[0]));
							sender.sendMessage(Messages.getMessage("SEND_KIT").replace("%player%", t.getName())
									.replace("%kit%", args[0]));
						}

					} else {
						sender.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", args[0]));
					}
				} else {
					sender.sendMessage(Messages.getMessage("KIT_COMMAND_USAGE_TARGET"));
				}
			} else {
				sender.sendMessage(Messages.getMessage("NOPERMISSION_COMMAND").replace("%player%", sender.getName())
						.replace("%permission%", "godkits.sendkit"));
			}
		}
		return false;
	}

	public static boolean isINT(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
