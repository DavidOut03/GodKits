package me.godkits.API;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

	private static Map<String, CooldownManager> cooldown = new HashMap<String, CooldownManager>();
	private long start;
	private final int cooldownTime;
	private final UUID uuid;
	private final String kit;

	public CooldownManager(UUID uuid, String kit, int timeInSeconds) {
		this.uuid = uuid;
		this.kit = kit;
		this.cooldownTime = timeInSeconds;
	}

	public static boolean isOnCooldown(UUID uuid, String kit) {
		if (getRemainingTime(uuid, kit) > 1) {
			return true;
		} else {
			stop(uuid, kit);
			return false;
		}
	}

	private static void stop(UUID uuid, String kit) {
		cooldown.remove(uuid + kit);
	}

	public static CooldownManager getCooldown(UUID uuid, String kit) {
		return cooldown.get(uuid.toString() + kit);
	}

	public static int getRemainingTime(UUID uuid, String kit) {
		CooldownManager cooldown = getCooldown(uuid, kit);
		int f = -1;

		if (cooldown != null) {
			long now = System.currentTimeMillis();
			long cooldownTime = cooldown.start;
			int totaldelay = cooldown.cooldownTime;
			int r = (int) (now - cooldownTime) / 1000;
			f = (r - totaldelay) * (-1);
		}
		return f;
	}

	public void start() {
		this.start = System.currentTimeMillis();
		cooldown.put(this.uuid.toString() + this.kit, this);
		GodKitsApi api = new GodKitsApi();
		try {
			api.Cooldownyml.set("Users." + uuid + "." + this.kit, this.cooldownTime);
			api.Cooldownyml.save(api.CooldownFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
