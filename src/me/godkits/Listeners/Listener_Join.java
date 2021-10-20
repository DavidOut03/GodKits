package me.godkits.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.godkits.API.Kits;
import me.godkits.Core.Main;

public class Listener_Join implements Listener {

	Main plugin = Main.getPlugin(Main.class);

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		if (!e.getPlayer().hasPlayedBefore()) {
			if (!plugin.getConfig().getString("Settings.default-kit").equals("none")) {
				Kits.sendKit(e.getPlayer(), plugin.getConfig().getString("Settings.default-kit"));
			}
		}
	}

}
