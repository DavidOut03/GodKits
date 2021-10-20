package me.godkits.GUI;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class GUI_SetUpKitInventory implements Listener {

	public static void sendEditorGUI(Player p, String kit) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		String guiname = "kit-setinventory";
		if (plugin.getConfig().getString("GUI." + guiname + ".gui-name") != null) {
			Inventory inv = Bukkit.createInventory(null, 54,
					Chat.format(plugin.getConfig().getString("GUI.kit-setinventory.gui-name")));

			ItemStack glaspane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			inv.setItem(36, glaspane);
			inv.setItem(37, glaspane);
			inv.setItem(38, glaspane);
			inv.setItem(39, glaspane);
			inv.setItem(40, glaspane);
			inv.setItem(41, glaspane);
			inv.setItem(42, glaspane);
			inv.setItem(43, glaspane);
			inv.setItem(44, glaspane);
			inv.setItem(49, glaspane);

			ItemStack save = new ItemStack(Material.EMERALD_BLOCK);
			ItemMeta savem = save.getItemMeta();
			ArrayList<String> savelore = new ArrayList<>();
			savelore.add(Chat.format("&7Click to save the kit."));
			savem.setLore(savelore);
			savem.setDisplayName(Chat.format("&a&lSave"));
			save.setItemMeta(savem);
			inv.setItem(45, save);

			ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta cancelm = cancel.getItemMeta();
			ArrayList<String> cancellore = new ArrayList<>();
			cancellore.add(Chat.format("&7Click to cancel this action."));
			cancelm.setLore(cancellore);
			cancelm.setDisplayName(Chat.format("&c&lCancel"));
			cancel.setItemMeta(cancelm);
			inv.setItem(46, cancel);

			ItemStack chest = new ItemStack(Material.OAK_SIGN);
			ItemMeta chestm = chest.getItemMeta();
			chestm.setDisplayName(Chat.format("&e" + kit));
			ArrayList<String> chestlore = new ArrayList<>();
			chestlore.add(Chat.format("&7Current kit: &c" + api.Kitsyml.getString("Kits." + kit + ".GUI.displayname")));
			chestm.setLore(chestlore);
			chest.setItemMeta(chestm);
			inv.setItem(48, chest);

			if (api.Kitsyml.get("Kits." + kit + ".armor.helmet") instanceof ItemStack
					|| api.Kitsyml.get("Kits." + kit + ".armor.helmet") != null) {
				ItemStack helmet = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.helmet");
				inv.setItem(50, helmet);
			} else {
				ItemStack helmet = new ItemStack(Material.IRON_BARS);
				ItemMeta hm = helmet.getItemMeta();
				hm.setDisplayName(Chat.format("&cHelmet"));
				helmet.setItemMeta(hm);
				inv.setItem(50, helmet);
			}
			if (api.Kitsyml.get("Kits." + kit + ".armor.chestplate") instanceof ItemStack
					|| api.Kitsyml.get("Kits." + kit + ".armor.chestplate") != null) {
				ItemStack chestplate = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.chestplate");
				inv.setItem(51, chestplate);
			} else {
				ItemStack chestplate = new ItemStack(Material.IRON_BARS);
				ItemMeta hm = chestplate.getItemMeta();
				hm.setDisplayName(Chat.format("&cChestplate"));
				chestplate.setItemMeta(hm);
				inv.setItem(51, chestplate);
			}
			if (api.Kitsyml.get("Kits." + kit + ".armor.leggings") instanceof ItemStack
					|| api.Kitsyml.get("Kits." + kit + ".armor.leggings") != null) {
				ItemStack leggings = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.leggings");
				inv.setItem(52, leggings);
			} else {
				ItemStack leggings = new ItemStack(Material.IRON_BARS);
				ItemMeta hm = leggings.getItemMeta();
				hm.setDisplayName(Chat.format("&cLeggings"));
				leggings.setItemMeta(hm);
				inv.setItem(52, leggings);
			}
			if (api.Kitsyml.get("Kits." + kit + ".armor.boots") instanceof ItemStack
					|| api.Kitsyml.get("Kits." + kit + ".armor.boots") != null) {
				ItemStack boots = (ItemStack) api.Kitsyml.get("Kits." + kit + ".armor.boots");
				inv.setItem(53, boots);
			} else {
				ItemStack boots = new ItemStack(Material.IRON_BARS);
				ItemMeta hm = boots.getItemMeta();
				hm.setDisplayName(Chat.format("&cBoots"));
				boots.setItemMeta(hm);
				inv.setItem(53, boots);
			}

			ArrayList<ItemStack> content = (ArrayList<ItemStack>) api.Kitsyml.get("Kits." + kit + ".content");
			ItemStack[] items = content.toArray(new ItemStack[0]);
			for (ItemStack item : items) {
				inv.addItem(item);
			}
			p.openInventory(inv);
		} else {
			p.sendMessage(Messages.getMessage("GUI_NAME_NOT_FOUND").replace("%gui%", guiname));
		}

	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();

		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if (e.getWhoClicked() != null) {
				if (e.getWhoClicked() instanceof Player) {
					if (e.getClickedInventory() != p.getInventory()) {
						if (e.getView().getTitle() != null) {
							if (e.getView().getTitle().equalsIgnoreCase(
									Chat.format(plugin.getConfig().getString("GUI.kit-setinventory.gui-name")))) {
								if (e.getSlot() >= 36 && e.getSlot() <= 49) {
									e.setCancelled(true);
									if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
										p.closeInventory();
										String kit = ChatColor.stripColor(
												e.getClickedInventory().getItem(48).getItemMeta().getDisplayName());
										p.sendMessage(Messages.getMessage("KIT_SETINVENTORY")
												.replace("%player%", p.getName()).replace("%kit%", kit));

										ArrayList<ItemStack> items = new ArrayList<>();
										for (int slot = 0; slot <= 35; slot++) {
											if (e.getClickedInventory().getItem(slot) != null) {
												items.add(e.getClickedInventory().getItem(slot));
											}

										}

										ItemStack[] content = items.toArray(new ItemStack[0]);

										try {
											api.Kitsyml.set("Kits." + kit + ".content", content);
											if (e.getClickedInventory().getItem(50).getType() != Material.IRON_BARS) {
												api.Kitsyml.set("Kits." + kit + ".armor.helmet",
														e.getClickedInventory().getItem(50));
											} else {
												api.Kitsyml.set("Kits." + kit + ".armor.helmet", null);
											}
											if (e.getClickedInventory().getItem(51).getType() != Material.IRON_BARS) {
												api.Kitsyml.set("Kits." + kit + ".armor.chestplate",
														e.getClickedInventory().getItem(51));
											} else {
												api.Kitsyml.set("Kits." + kit + ".armor.chestplate", null);
											}
											if (e.getClickedInventory().getItem(52).getType() != Material.IRON_BARS) {
												api.Kitsyml.set("Kits." + kit + ".armor.leggings",
														e.getClickedInventory().getItem(52));
											} else {
												api.Kitsyml.set("Kits." + kit + ".armor.leggings", null);
											}
											if (e.getClickedInventory().getItem(53).getType() != Material.IRON_BARS) {
												api.Kitsyml.set("Kits." + kit + ".armor.boots",
														e.getClickedInventory().getItem(53));
											} else {
												api.Kitsyml.set("Kits." + kit + ".armor.boots", null);
											}

											api.Kitsyml.save(api.KitsFile);
										} catch (IOException ex) {
											ex.printStackTrace();
										}
									} else if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
										p.closeInventory();
									} else if (e.getCurrentItem().getType() == Material.OAK_SIGN) {
										e.setCancelled(true);
									}
								} else if (e.getSlot() >= 49 && e.getSlot() <= 54) {
									if (e.getCurrentItem() != null) {
										if (e.getCursor().getType() == Material.AIR || e.getCursor() == null) {
											if (e.getCurrentItem().getType() != Material.IRON_BARS) {
												if (e.getSlot() == 50) {
													if (e.getCurrentItem().getType() != Material.IRON_BARS) {
														ItemStack helmet = new ItemStack(Material.IRON_BARS);
														ItemMeta hm = helmet.getItemMeta();
														hm.setDisplayName(Chat.format("&cHelmet"));
														helmet.setItemMeta(hm);
														e.setCursor(e.getCurrentItem());
														e.setCurrentItem(helmet);
														e.setCancelled(true);
													} else {
														e.setCancelled(true);
													}
												} else if (e.getSlot() == 51) {
													if (e.getCurrentItem().getType() != Material.IRON_BARS) {
														ItemStack chestplate = new ItemStack(Material.IRON_BARS);
														ItemMeta hm = chestplate.getItemMeta();
														hm.setDisplayName(Chat.format("&cChestplate"));
														chestplate.setItemMeta(hm);
														e.setCursor(e.getCurrentItem());
														e.setCurrentItem(chestplate);
														e.setCancelled(true);
													} else {
														e.setCancelled(true);
													}
												} else if (e.getSlot() == 52) {
													ItemStack leggings = new ItemStack(Material.IRON_BARS);
													if (e.getCurrentItem().getType() != Material.IRON_BARS) {
														ItemMeta hm = leggings.getItemMeta();
														hm.setDisplayName(Chat.format("&cLeggings"));
														leggings.setItemMeta(hm);
														e.setCursor(e.getCurrentItem());
														e.setCurrentItem(leggings);
														e.setCancelled(true);
													} else {
														e.setCancelled(true);
													}
												} else if (e.getSlot() == 53) {
													if (e.getCurrentItem().getType() != Material.IRON_BARS) {
														ItemStack boots = new ItemStack(Material.IRON_BARS);
														ItemMeta hm = boots.getItemMeta();
														hm.setDisplayName(Chat.format("&cBoots"));
														boots.setItemMeta(hm);
														e.setCursor(e.getCurrentItem());
														e.setCurrentItem(boots);
														e.setCancelled(true);
													} else {
														e.setCancelled(true);
													}
												}
											}
										} else {
											if (e.getCurrentItem().getType() == Material.IRON_BARS) {
												if (e.getCurrentItem().getItemMeta() != null) {
													if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
														if (e.getCurrentItem().getItemMeta().getDisplayName()
																.equalsIgnoreCase(Chat.format("&cHelmet"))) {
															e.setCancelled(true);
															if (e.getCursor() != null
																	|| e.getCursor().getType() != Material.AIR) {
																e.setCurrentItem(e.getCursor());
																e.setCursor(null);
															} else {
																e.setCancelled(true);
															}
														} else if (e.getCurrentItem().getItemMeta().getDisplayName()
																.equalsIgnoreCase(Chat.format("&cChestplate"))) {
															e.setCancelled(true);
															if (e.getCursor() != null
																	|| e.getCursor().getType() != Material.AIR) {
																e.setCurrentItem(e.getCursor());
																e.setCursor(null);
															} else {
																e.setCancelled(true);
															}
														} else if (e.getCurrentItem().getItemMeta().getDisplayName()
																.equalsIgnoreCase(Chat.format("&cLeggings"))) {
															e.setCancelled(true);
															if (e.getCursor() != null
																	|| e.getCursor().getType() != Material.AIR) {
																e.setCurrentItem(e.getCursor());
																e.setCursor(null);
															} else {
																e.setCancelled(true);
															}
														} else if (e.getCurrentItem().getItemMeta().getDisplayName()
																.equalsIgnoreCase(Chat.format("&cBoots"))) {
															e.setCancelled(true);
															if (e.getCursor() != null
																	|| e.getCursor().getType() != Material.AIR) {
																e.setCurrentItem(e.getCursor());
																e.setCursor(null);
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
				}
			}
		}
	}

}
