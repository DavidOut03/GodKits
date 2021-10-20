package me.godkits.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.godkits.API.CooldownManager;
import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Format.Chat;
import me.godkits.Format.Time;
import me.godkits.GUI.GUI_Kits;
import me.godkits.GUI.GUI_PreviewKit;
import me.godkits.Messages.Messages;

public class Listener_SignClick implements Listener {

	@EventHandler
	public void onInterackt(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		GodKitsApi api = new GodKitsApi();
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType().toString().contains("SIGN")) {
					Sign sign = (Sign) e.getClickedBlock().getState();
					if(sign.getLine(0).equalsIgnoreCase(Chat.format("&9[kits]"))) {
						e.setCancelled(true);
						GUI_Kits.sendGUI(p);
					} else if(sign.getLine(0).equalsIgnoreCase(Chat.format("&9[preview-kit]"))) {
						if(sign.getLine(1) != null) {
							if(api.Kitsyml.getConfigurationSection("Kits").contains(sign.getLine(1))) {
								GUI_PreviewKit.sendKitGUI(p, sign.getLine(1));
							} else {
								p.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%player%", p.getName()).replace("%kit%", sign.getLine(1)));
							}
						}
					} else if(sign.getLine(0).equalsIgnoreCase(Chat.format("&9[kit]"))) {
						String kit = sign.getLine(1);
						if(api.Kitsyml.getConfigurationSection("Kits").contains(kit)) {
							if (api.Kitsyml.getString("Kits." + kit + ".permission") != null
									&& api.Kitsyml.get("Kits." + kit + ".cooldown") != null) {
								if (api.Kitsyml.getString("Kits." + kit + ".permission").equals("none")) {
									if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")
											|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
										if (!api.hasPermission(p, "godkits.bypasscooldown")) {
											if (isINT(api.Kitsyml.getString("Kits." + kit + ".cooldown")) == true) {

												if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
													p.sendMessage(Messages
															.getMessage("ON_KIT_COOLDOWN")
															.replace("%cooldown%",
																	Time.format(CooldownManager
																			.getRemainingTime(p.getUniqueId(), kit)))
															.replace("%player%", p.getName()).replace("%kit%", kit));
												} else {
													CooldownManager cooldown = new CooldownManager(p.getUniqueId(), kit,
															api.Kitsyml.getInt("Kits." + kit + ".cooldown"));
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
									if (api.hasPermission(p, api.Kitsyml.getString("Kits." + kit + ".permission"))) {
										if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")
												|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
											if (!api.hasPermission(p, "godkits.bypasscooldown")) {
												if (isINT(api.Kitsyml.getString("Kits." + kit + ".cooldown")) == true) {
													if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
														p.sendMessage(
																Messages.getMessage("ON_KIT_COOLDOWN")
																		.replace("%cooldown%", Time.format(CooldownManager
																				.getRemainingTime(p.getUniqueId(), kit)))
																		.replace("%player%", p.getName())
																		.replace("%kit%", kit));
													} else {
														CooldownManager cooldown = new CooldownManager(p.getUniqueId(), kit,
																api.Kitsyml.getInt("Kits." + kit + ".cooldown"));
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
										p.sendMessage(Messages.getMessage("NOPERMISSION_KIT").replace("%kit%", kit)
												.replace("%permission%",
														api.Kitsyml.getString("Kits." + kit + ".permission"))
												.replace("%player%", p.getName()));
									}
								}
							} else {
								p.sendMessage(Messages.getMessage("RESET_KIT").replace("%kit%", kit));
							}
						} else {
							p.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", kit));
						}
						}
					}
 				} 
			
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
