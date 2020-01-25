package pl.blackwaterapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;
import pl.blackwaterapi.API;
import pl.blackwaterapi.data.Config;
import pl.blackwaterapi.timer.ItemBuilderTimer;

public class APIGui {
	
	public static void OpenMenu(Player p){
		Inventory inv = Bukkit.createInventory(p, 54,Util.fixColor(Config.API_GUI_NAME));
		ItemBuilder air = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)7).setTitle(Util.fixColor("&2"));
		ItemBuilder green = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)5).setTitle(Util.fixColor("&2"));
		ItemBuilder blue = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short)9).setTitle(Util.fixColor("&2"));
		ItemBuilder tps = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(Util.fixColor("&2BW-API &8» &7TPS: &6" + Ticking.getTPS()));
		ItemBuilder lag = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(Util.fixColor("&2BW-API &8» &7Try: &6Delete Lags"))
					.addLore(Util.fixColor("&8» &7Kill &6All Entities &2(Animals,Monsters,Items)"))
					.addLore(Util.fixColor("&8» &7Disable &6Entities &2(Animals,Monsters,Items)"))
					.addLore(Util.fixColor("&8» &7Refresh &6Chunks &2(" + p.getWorld().getLoadedChunks().length + ")"))
					.addLore(Util.fixColor("&8» &7Enable &6lagg halt &2(ClearLags-Plugin)"));
		ItemBuilder MYSQL = new ItemBuilder(Material.CHEST).setTitle(Util.fixColor("&2BW-API &8» &7MYSQL &6Informations"))
					.addLore(Util.fixColor("&8» &7MySQL Host: &6" + Config.DATABASE_MYSQL_HOST))
					.addLore(Util.fixColor("&8» &7MySQL PORT: &6" + Config.DATABASE_MYSQL_PORT))
					.addLore(Util.fixColor("&8» &7MySQL User: &6" + Config.DATABASE_MYSQL_USER))
					.addLore(Util.fixColor("&8» &7MySQL Password: &6" + ChatColor.MAGIC + "XO#WPWMSOWKSQB@SFW@" + Config.DATABASE_MYSQL_PASS + "3!@DSDIWNSAPWMSOWKSQB"))
					.addLore(Util.fixColor("&8» &7MySQL Database Name: &6" + Config.DATABASE_MYSQL_NAME))
					.addLore(Util.fixColor("&8» &7MySQL Table Prefix: &6" + Config.DATABASE_TABLEPREFIX));
		ItemBuilder SERVER = new ItemBuilder(Material.FURNACE).setTitle(Util.fixColor("&2BW-API &8» &7Server &6Informations"))
					.addLore(Util.fixColor("&8» &7Server IP: &6" + ChatColor.MAGIC + "123.321.412.421" + Bukkit.getServer().getIp() + "331.312.682.912"))
					.addLore(Util.fixColor("&8» &7Server PORT: &6" + Bukkit.getServer().getPort()))
					.addLore(Util.fixColor("&8» &7Server Version: &6" + Bukkit.getServer().getVersion()))
					.addLore(Util.fixColor("&8» &7Server BukkitVersion: &6" + Bukkit.getServer().getBukkitVersion()))
					.addLore(Util.fixColor("&8» &7Server Online Mode: &6" + String.valueOf(Bukkit.getServer().getOnlineMode()).toUpperCase()))
					.addLore(Util.fixColor("&8» &7Server Loaded Worlds: &6" + getWorlds().toString().replace("[", "").replace("]", "")))
					.addLore(Util.fixColor("&8» &7Server Operators: &6" + getOperators().toString().replace("[", "").replace("]", "")));
		p.openInventory(inv);
		new ItemBuilderTimer(air, 0, inv).runTaskLater(API.getPlugin(), 4L);
		new ItemBuilderTimer(air, 1, inv).runTaskLater(API.getPlugin(), 2*4L);
		new ItemBuilderTimer(air, 2, inv).runTaskLater(API.getPlugin(), 3*4L);
		new ItemBuilderTimer(air, 3, inv).runTaskLater(API.getPlugin(), 4*4L);
		new ItemBuilderTimer(air, 4, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(air, 5, inv).runTaskLater(API.getPlugin(), 6*4L);
		new ItemBuilderTimer(air, 6, inv).runTaskLater(API.getPlugin(), 7*4L);
		new ItemBuilderTimer(air, 7, inv).runTaskLater(API.getPlugin(), 8*4L);
		new ItemBuilderTimer(air, 8, inv).runTaskLater(API.getPlugin(), 9*4L);
		new ItemBuilderTimer(green, 10, inv).runTaskLater(API.getPlugin(), 2*4L);
		new ItemBuilderTimer(MYSQL, 12, inv).runTaskLater(API.getPlugin(), 10*4L);
		new ItemBuilderTimer(blue, 13, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(green, 18, inv).runTaskLater(API.getPlugin(), 4L);
		new ItemBuilderTimer(tps, 19, inv).runTaskLater(API.getPlugin(), 10*4L);
		new ItemBuilderTimer(green, 20, inv).runTaskLater(API.getPlugin(), 3*4L);
		new ItemBuilderTimer(air, 21, inv).runTaskLater(API.getPlugin(), 4*4L);
		new ItemBuilderTimer(blue, 22, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(green, 28, inv).runTaskLater(API.getPlugin(), 2*4L);
		new ItemBuilderTimer(air, 30, inv).runTaskLater(API.getPlugin(), 4*4L);
		new ItemBuilderTimer(blue, 31, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(green, 36, inv).runTaskLater(API.getPlugin(), 4L);
		new ItemBuilderTimer(lag, 37, inv).runTaskLater(API.getPlugin(), 10*4L);
		new ItemBuilderTimer(green, 38, inv).runTaskLater(API.getPlugin(), 3*4L);
		new ItemBuilderTimer(SERVER, 39, inv).runTaskLater(API.getPlugin(), 10*4L);
		new ItemBuilderTimer(blue, 40, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(air, 45, inv).runTaskLater(API.getPlugin(), 4L);
		new ItemBuilderTimer(air, 46, inv).runTaskLater(API.getPlugin(), 2*4L);
		new ItemBuilderTimer(air, 47, inv).runTaskLater(API.getPlugin(), 3*4L);
		new ItemBuilderTimer(air, 48, inv).runTaskLater(API.getPlugin(), 4*4L);
		new ItemBuilderTimer(air, 49, inv).runTaskLater(API.getPlugin(), 5*4L);
		new ItemBuilderTimer(air, 50, inv).runTaskLater(API.getPlugin(), 6*4L);
		new ItemBuilderTimer(air, 51, inv).runTaskLater(API.getPlugin(), 7*4L);
		new ItemBuilderTimer(air, 52, inv).runTaskLater(API.getPlugin(), 8*4L);
		new ItemBuilderTimer(air, 53, inv).runTaskLater(API.getPlugin(), 9*4L);
	}
	public static List<String> getOperators(){
		List<String> list = new ArrayList<>();
		for(OfflinePlayer player : Bukkit.getServer().getOperators()){
			list.add(player.getName());
		}
		return list;
	}
	public static List<String> getWorlds(){
		List<String> list = new ArrayList<>();
		for(World w : Bukkit.getWorlds()){
			list.add(w.getName());
		}
		return list;
	}

}
