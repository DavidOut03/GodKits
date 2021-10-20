package me.godkits.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Core.Main;
import me.godkits.GUI.GUI_Kits;
import me.godkits.Messages.Messages;

public class Command_Kits implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Main plugin = JavaPlugin.getPlugin(Main.class);
		GodKitsApi api = new GodKitsApi();
		if (cmd.getName().equalsIgnoreCase("kits")) {
			if (sender instanceof Player) {
				if (plugin.getConfig().getBoolean("Settings.gui-enabled") == true) {
					Player p = (Player) sender;
					GUI_Kits.sendGUI(p);
				} else if (plugin.getConfig().getBoolean("Settings.gui-enabled") == false) {
					ArrayList<String> allowedkits = new ArrayList<>();
					for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
						if (api.Kitsyml.getString("Kits." + kit + ".permission").equals("none")
								|| api.hasPermission(sender, api.Kitsyml.getString("Kits." + kit + ".permission"))) {
							allowedkits.add(kit);
						}
					}
					sender.sendMessage(Messages.getMessage("KIT_LIST").replace("%kits%", allowedkits.toString()));
				}
			} else {
				ArrayList<String> allowedkits = new ArrayList<>();
				for (String kit : api.Kitsyml.getConfigurationSection("Kits").getKeys(false)) {
					if (api.Kitsyml.getString("Kits." + kit + ".permission").equals("none")
							|| api.hasPermission(sender, api.Kitsyml.getString("Kits." + kit + ".permission"))) {
						allowedkits.add(kit);
					}
				}
				sender.sendMessage(Messages.getMessage("KIT_LIST").replace("%kits%",
						allowedkits.toString().replace("[", "").replace("]", "")));
			}
		}
		return false;
	}

}
