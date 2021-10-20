package me.godkits.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.CooldownManager;
import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Format.Time;
import me.godkits.Messages.Messages;

public class GUI_Kits implements Listener {

	public static void sendGUI(Player p) {
		Main plugin = JavaPlugin.getPlugin(Main.class);

		GodKitsApi api = new GodKitsApi();

		String guiname = "kits";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, plugin.getConfig().getInt("GUI.kits.rows") * 9,Chat.format(plugin.getConfig().getString("GUI.kits.gui-name")));
			ArrayList<ItemStack> items = (ArrayList<ItemStack>) api.KitsGUIyml.get("GUI.content");
			ItemStack[] content = items.toArray(new ItemStack[0]);
			if (content.length <= inv.getSize() || content.length == inv.getSize()) {
				inv.setContents(content);
			} else {
				if (api.hasPermission(p, "godkits.errors")) {
					p.sendMessage(Messages.getMessage("ERROR_GUI_SIZE"));
				} else {
					p.sendMessage(Messages.getMessage("CONTACT_STAFF"));
				}
			}

			for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {

				if (isINT(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")) == true) {
					ItemStack icon = new ItemStack(Material.getMaterial(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,(short) api.Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
					ItemMeta iconmeta = icon.getItemMeta();
					List<String> iconlore = api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					ArrayList<String> newlore = new ArrayList<>();
					for (String lore : iconlore) {
						if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")
								|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
							if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
								int seconds = CooldownManager.getRemainingTime(p.getUniqueId(), kit);
								newlore.add(Chat.format(lore).replace("%cooldown%",Time.format(CooldownManager.getRemainingTime(p.getUniqueId(), kit))).replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
							} else {
								newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
							}
						} else if (api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
							newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "No Cooldown"));
						}
					}
					iconmeta.setDisplayName(Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
					iconmeta.setLore(newlore);
					icon.setItemMeta(iconmeta);
					inv.addItem(icon);

				} else if (isINT(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")) == false) {
					ItemStack icon = new ItemStack(Material.getMaterial(api.Kitsyml.getString("Kits." + kit + ".GUI.icon")), 1,(short) api.Kitsyml.getInt("Kits." + kit + ".GUI.iconID"));
					ItemMeta iconmeta = icon.getItemMeta();
					List<String> iconlore = api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
					ArrayList<String> newlore = new ArrayList<>();
					for (String lore : iconlore) {

						if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")
								|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
							if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
								int seconds = CooldownManager.getRemainingTime(p.getUniqueId(), kit);
								newlore.add(Chat.format(lore).replace("%cooldown%",Time.format(CooldownManager.getRemainingTime(p.getUniqueId(), kit))).replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
							} else {
								newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
							}
						} else if (api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
							newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "No Cooldown"));
						}
					}
					iconmeta.setDisplayName(Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
					iconmeta.setLore(newlore);
					icon.setItemMeta(iconmeta);
					inv.addItem(icon);

				}

			}
			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GodKitsApi api = new GodKitsApi();
		Main plugin = JavaPlugin.getPlugin(Main.class);
		Player p = (Player) e.getWhoClicked();

		if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if (e.getWhoClicked() != null) {
				if (e.getWhoClicked() instanceof Player) {
					if (e.getClickedInventory() != p.getInventory()) {
						if (e.getView().getTitle()
								.equalsIgnoreCase(Chat.format(plugin.getConfig().getString("GUI.kits.gui-name")))) {
							e.setCancelled(true);
							if (e.getCurrentItem().getItemMeta() != null) {
								if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
									if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem() != null) {
										for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
											if (Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")).equalsIgnoreCase(Chat.format(e.getCurrentItem().getItemMeta().getDisplayName()))) {
												if (e.getClick() == ClickType.LEFT) {
													if (api.Kitsyml.getConfigurationSection("Kits").contains(kit)) {
														if (api.Kitsyml.getString("Kits." + kit + ".permission") != null && api.Kitsyml.get("Kits." + kit + ".cooldown") != null) {
															if (api.Kitsyml.getString("Kits." + kit + ".permission").equals("none")) {
																if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
																	if (!api.hasPermission(p,"godkits.bypasscooldown")) {
																		if (isINT(api.Kitsyml.getString("Kits." + kit + ".cooldown")) == true) {
																			if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
																				p.sendMessage(Messages.getMessage("ON_KIT_COOLDOWN").replace("%cooldown%", Time.format(CooldownManager.getRemainingTime(p.getUniqueId(),kit))).replace("%player%",p.getName()).replace("%kit%", kit));
																				p.closeInventory();
																			} else {
																				CooldownManager cooldown = new CooldownManager(
																						p.getUniqueId(), kit,
																						api.Kitsyml.getInt("Kits." + kit+ ".cooldown"));
																				cooldown.start();
																				Kits.sendKit(p, kit);
																			}
																		} else {
																			p.sendMessage(Chat.format("&c" + api.Kitsyml.getString("Kits."+ kit + ".cooldown"+ " is not a number.")));
																		}
																	} else {
																		Kits.sendKit(p, kit);
																	}
																} else {
																	Kits.sendKit(p, kit);
																}
															} else {
																if (api.hasPermission(p, api.Kitsyml.getString("Kits." + kit + ".permission"))) {
																	if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
																		if (!api.hasPermission(p,"godkits.bypasscooldown")) {
																			if (isINT(api.Kitsyml.getString("Kits."+ kit + ".cooldown")) == true) {
																				if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
																					p.sendMessage(Messages.getMessage("ON_KIT_COOLDOWN").replace("%cooldown%", Time.format(CooldownManager.getRemainingTime(p.getUniqueId(),kit))).replace("%player%",p.getName()).replace("%kit%", kit));
																					p.closeInventory();
																				} else {
																					CooldownManager cooldown = new CooldownManager(
																							p.getUniqueId(), kit,
																							api.Kitsyml.getInt("Kits."+ kit+ ".cooldown"));
																					cooldown.start();
																					Kits.sendKit(p, kit);

																				}
																			} else {
																				p.sendMessage(Chat.format("&c"+ api.Kitsyml.getString("Kits."+ kit + ".cooldown"+ " is not a number.")));
																			}
																		} else {
																			Kits.sendKit(p, kit);
																		}
																	} else {
																		Kits.sendKit(p, kit);
																	}
																} else {
																	p.sendMessage(Messages.getMessage("NOPERMISSION_KIT").replace("%kit%", kit).replace("%permission%",api.Kitsyml.getString("Kits." + kit+ ".permission")).replace("%player%", p.getName()));
																}
															}
														} else {
															p.sendMessage(Messages.getMessage("RESET_KIT").replace("%kit%", kit));
														}
													} else {
														p.sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", kit));
													}
													
													ItemStack icon = e.getCurrentItem();
													ItemMeta iconmeta = icon.getItemMeta();
													List<String> iconlore = api.Kitsyml.getStringList("Kits." + kit + ".GUI.lore");
													ArrayList<String> newlore = new ArrayList<>();
													for (String lore : iconlore) {

														if (!api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")
																|| !api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
															if (CooldownManager.isOnCooldown(p.getUniqueId(), kit)) {
																int seconds = CooldownManager.getRemainingTime(p.getUniqueId(), kit);
																newlore.add(Chat.format(lore).replace("%cooldown%",Time.format(CooldownManager.getRemainingTime(p.getUniqueId(), kit))).replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
															} else {
																newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "" + Time.format(Kits.getCooldown(kit))));
															}
														} else if (api.Kitsyml.get("Kits." + kit + ".cooldown").equals("none")|| api.Kitsyml.get("Kits." + kit + ".cooldown").equals("-1")) {
															newlore.add(Chat.format(lore).replace("%cooldown%", "Now Avalible").replace("%permission%", Kits.getPermission(kit)).replace("%kitcooldown%", "No Cooldown"));
														}
													}
													iconmeta.setDisplayName(Chat.format(api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
													iconmeta.setLore(newlore);
													icon.setItemMeta(iconmeta);
													e.setCurrentItem(icon);

												} else if (e.getClick() == ClickType.RIGHT) {
													GUI_PreviewKit.sendKitGUI(p, kit);
												}
											} else {
												e.setCancelled(true);
											}
										}
									}
								}
							}
						}
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
