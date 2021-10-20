package me.godkits.Listeners;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.godkits.API.GodKitsApi;
import me.godkits.API.Kits;
import me.godkits.Format.Chat;
import me.godkits.Messages.Messages;

public class Listener_SignChance implements Listener {

	@EventHandler
	public void onChance(SignChangeEvent e) {
		GodKitsApi api = new GodKitsApi();
		if(api.hasPermission(e.getPlayer(), "signplace")) {
		if(e.getLine(0).equalsIgnoreCase("[kits]")) {
			e.setLine(0, Chat.format("&9[kits]"));
		} else if(e.getLine(0).equalsIgnoreCase("[kit]")) {
			if(api.kitExist(e.getLine(1))) {
				e.setLine(0, Chat.format("&9[kit]"));
				e.setLine(1, Kits.getKitName(e.getLine(1)));
			} else {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", e.getLine(1)));
			}
	
		} else if(e.getLine(0).equals("[kit-preview]") || e.getLine(0).equals("[kitpreview]") 
				|| e.getLine(0).equals("[kitpre]") || e.getLine(0).equals("[kit-preview]") || 
				e.getLine(0).equals("[preview-kit]") || e.getLine(0).equals("[previewkit]") 
				|| e.getLine(0).equals("[prekit]") || e.getLine(0).equals("[pre-kit]")) {
			if(api.kitExist(e.getLine(1))) {
			e.setLine(0, Chat.format("&9[preview-kit]"));
			e.setLine(1, Kits.getKitName(e.getLine(1)));
			} else {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Messages.getMessage("KIT_NOT_EXIST").replace("%kit%", e.getLine(1)));
			}
		} else {
			e.setLine(0, Chat.format(e.getLine(0)));
			e.setLine(1, Chat.format(e.getLine(1)));
			e.setLine(2, Chat.format(e.getLine(2)));
			e.setLine(3, Chat.format(e.getLine(3)));
		}
	}
	}
	
}
