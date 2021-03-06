package me.armar.plugins.autorank.permissions;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.permissions.handlers.GroupManagerHandler;
import me.armar.plugins.autorank.permissions.handlers.PermissionsBukkitHandler;
import me.armar.plugins.autorank.permissions.handlers.VaultPermissionsHandler;
import net.milkbowl.vault.Vault;

import org.bukkit.plugin.Plugin;

/*
 * PermissionsPluginManager sort the tasks of removing/adding a player to a group depending
 * on the permissions plugin. 
 * For now it supports Vault and explicit GroupManager.
 *  
 */

/**
 * PermissionsPluginManager manages what permission handler should be given. It
 * just does basic checks of availability and calculates what permissions plugin
 * suits best.
 * 
 * It can choose from GroupManager, PermissionsBukkit and Vault.
 * 
 * @author Staartvin
 * 
 */
public class PermissionsPluginManager {

	private final Autorank plugin;
	private PermissionsHandler permissionPlugin;

	public PermissionsPluginManager(final Autorank plugin) {
		this.plugin = plugin;
		if (findVault(plugin)) {
			Autorank.logMessage("Vault Hooked!");
		} else {
			Autorank.logMessage("WARNING Vault was not found!");
		}
		searchPermPlugin();
	}

	private void searchPermPlugin() {
		if (findGroupManager(plugin)) {
			// use Groupmanager
			permissionPlugin = new GroupManagerHandler(plugin);
		} else if (findPermissionsBukkit(plugin)) {
			// Use PermissionsBukkit
			permissionPlugin = new PermissionsBukkitHandler(plugin);
		} else {
			// use Vault
			permissionPlugin = new VaultPermissionsHandler(plugin);
		}
	}

	public PermissionsHandler getPermissionPlugin() {
		return permissionPlugin;
	}

	protected boolean findVault(final Autorank plugin) {
		final Plugin x = plugin.getServer().getPluginManager()
				.getPlugin("Vault");
		if (x != null & x instanceof Vault) {
			return true;
		}
		return false;
	}

	protected boolean findGroupManager(final Autorank plugin) {
		final Plugin x = plugin.getServer().getPluginManager()
				.getPlugin("GroupManager");
		if (x != null) {
			return true;
		}
		return false;
	}

	protected boolean findPermissionsBukkit(final Autorank plugin) {
		final Plugin x = plugin.getServer().getPluginManager()
				.getPlugin("PermissionsBukkit");
		if (x != null) {
			return true;
		}
		return false;
	}
}
