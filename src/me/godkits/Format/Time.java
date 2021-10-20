package me.godkits.Format;

import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.Core.Main;

public class Time {

	public static String format(int seconds) {
		Main plugin = JavaPlugin.getPlugin(Main.class);

		String Days = "";
		String Hours = "";
		String Minutes = "";
		String Seconds = "";

		int days = seconds / (60 * 60 * 24);
		if (days == 1) {
			Days = days + " day";
		} else if (days >= 1) {
			Days = days + " days";
		}

		seconds -= days * (60 * 60 * 24);

		int hours = seconds / (60 * 60);
		if (hours == 1) {
			Hours = hours + " hour";
		} else if (hours >= 1) {
			Hours = hours + " hours";
		}
		seconds -= hours * (60 * 60);

		int minutes = seconds / 60;
		if (minutes == 1) {
			Minutes = minutes + " minute";
		} else if (minutes >= 1) {
			Minutes = minutes + " minutes";
		}

		seconds -= minutes * 60;
		if (seconds == 1) {
			Seconds = seconds + " second";
		} else if (seconds >= 1) {
			Seconds = seconds + " seconds";
		}

		if (plugin.getConfig().getString("Settings.time-format") != null) {
			String format = plugin.getConfig().getString("Settings.time-format").replace("%days%", Days)
					.replace("%hours%", Hours).replace("%minutes%", Minutes).replace("%seconds%", Seconds);
			return format.trim();
		} else {
			String format = "" + Days + " " + Hours + " " + minutes + " " + Seconds;
			return format.trim();
		}

	}
}
