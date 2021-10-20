package me.godkits.Core;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.godkits.API.GodKitsApi;
import me.godkits.Commands.Command_Godkits;
import me.godkits.Commands.Command_Kit;
import me.godkits.Commands.Command_Kits;
import me.godkits.Commands.Command_PlaySound;
import me.godkits.Commands.Command_Sounds;
import me.godkits.GUI.GUI_KitSettings;
import me.godkits.GUI.GUI_Kits;
import me.godkits.GUI.GUI_PreviewKit;
import me.godkits.GUI.GUI_SetUpDefaultKit;
import me.godkits.GUI.GUI_SetUpExtraSettings;
import me.godkits.GUI.GUI_SetUpGUI;
import me.godkits.GUI.GUI_SetUpIcon;
import me.godkits.GUI.GUI_SetUpKitGUI;
import me.godkits.GUI.GUI_SetUpKitInventory;
import me.godkits.GUI.GUI_SetUpMessages;
import me.godkits.GUI.GUI_SettingsMenu;
import me.godkits.Listeners.Listener_Join;
import me.godkits.Listeners.Listener_OpenPackage;
import me.godkits.Listeners.Listener_SignChance;
import me.godkits.Listeners.Listener_SignClick;
import me.godkits.Messages.Messages;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		GodKitsApi api = new GodKitsApi();
		api.getFolders();
		api.getFiles();
		registerCommands();
		registerlisteners();
		registerGUI();
		registerTabCompletetor();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(Messages.getMessage("DISABLED_PLUGIN"));
	}

	private void registerCommands() {
		getCommand("godkits").setExecutor(new Command_Godkits());
		getCommand("kit").setExecutor(new Command_Kit());
		getCommand("kits").setExecutor(new Command_Kits());
		getCommand("sendkit").setExecutor(new Command_Kit());
		getCommand("playsound").setExecutor(new Command_PlaySound());
		getCommand("sounds").setExecutor(new Command_Sounds());
	}

	private void registerTabCompletetor() {
		getCommand("godkits").setTabCompleter(new Command_Godkits());
		getCommand("sounds").setTabCompleter(new Command_Sounds());
		getCommand("playsound").setTabCompleter(new Command_PlaySound());
	}

	private void registerlisteners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Listener_Join(), this);
		pm.registerEvents(new Listener_SignChance(), this);
		pm.registerEvents(new Listener_SignClick(), this);
		pm.registerEvents(new Listener_OpenPackage(), this);

	}

	private void registerGUI() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new GUI_Kits(), this);
		pm.registerEvents(new GUI_KitSettings(), this);
		pm.registerEvents(new GUI_PreviewKit(), this);
		pm.registerEvents(new GUI_SettingsMenu(), this);
		pm.registerEvents(new GUI_SetUpDefaultKit(), this);
		pm.registerEvents(new GUI_SetUpExtraSettings(), this);
		pm.registerEvents(new GUI_SetUpGUI(), this);
		pm.registerEvents(new GUI_SetUpIcon(), this);
		pm.registerEvents(new GUI_SetUpKitGUI(), this);
		pm.registerEvents(new GUI_SetUpKitInventory(), this);
		pm.registerEvents(new GUI_SetUpMessages(), this);

	}
}
