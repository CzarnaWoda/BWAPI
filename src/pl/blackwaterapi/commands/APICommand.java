package pl.blackwaterapi.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import pl.blackwaterapi.data.Config;
import pl.blackwaterapi.utils.APIGui;
import pl.blackwaterapi.utils.Ticking;
import pl.blackwaterapi.utils.Util;

public class APICommand extends PlayerCommand implements Listener{
	@Getter public static List<String> tpsUsers = new ArrayList<>();
	public APICommand() {
		super("api", "Main API Command", "/api [livetps/historytps/plugins/manager]", "api.admin");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if(args.length == 0){
			return Util.sendMsg(p, "&4Blad: &cPoprawne uzycie: " + getUsage());
		}else{
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("livetps")){
					if(!getTpsUsers().contains(p.getName())){
					getTpsUsers().add(p.getName());
					Util.sendMsg(p, "&8[&2&lBlackWater-API&8] &e&lPodglad TPS - LiveTime zostal wlaczony !");
					}else{
						getTpsUsers().remove(p.getName());
						Util.sendMsg(p, "&8[&2&lBlackWater-API&8] &e&lPodglad TPS - LiveTime zostal wylaczony !");
					}
				}else if (args[0].equalsIgnoreCase("historytps")){
					int i = 1;
					Util.sendMsg(p, "&8[&2&lBlackWater-API&8] &cTPS History: ");
					for(Double d : Ticking.getHistory()){	
						Util.sendMsg(p, "&8" + i + ". &7Ticks Per Second: &6" + d);
						i++;
					}
				}else if (args[0].equalsIgnoreCase("plugins")){
					int i = 1;
					for(Plugin plugin : Bukkit.getPluginManager().getPlugins()){
						Util.SendRun_CommandTextComponent(p, Util.fixColor("&8" + i + ". &7Plugin Name: &6" + plugin.getName()), "/api plugins " + plugin.getName(), "&6&lClick to view more about this plugin");
						i++;
					}
				}else if (args[0].equalsIgnoreCase("manager")){
					APIGui.OpenMenu(p);
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("plugins") && isPlugin(args[1])){
					Plugin plugin = getPlugin(args[1]);
					Util.sendMsg(p, "&7Plugin name: &6" + plugin.getName());
					Util.sendMsg(p, "&7Plugin version: &6" + plugin.getDescription().getVersion());
					Util.sendMsg(p, "&7Plugin description: &6" + plugin.getDescription().getDescription());
					Util.sendMsg(p, "&7Plugin main: &6" + plugin.getDescription().getMain());
					Util.sendMsg(p, "&7Plugin authors: &6" + plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
				}
			}
		}
		return false;
	}
	private static boolean isPlugin(String s){
		for(Plugin pl : Bukkit.getPluginManager().getPlugins()){
			if(pl.getName().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	public static Plugin getPlugin(String name){
		for(Plugin pl : Bukkit.getPluginManager().getPlugins()){
			if(pl.getName().equalsIgnoreCase(name)){
				return pl;
			}
		}
		return null;
	}
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		if(Util.fixColor(Config.API_GUI_NAME).equalsIgnoreCase(e.getInventory().getName())){
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            if (item != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    if (meta.getDisplayName() != null && meta.getDisplayName().equals(Util.fixColor("&2BW-API &8» &7Try: &6Delete Lags"))) {
                    Util.sendMsg(p, "&cRemove entities...");
                    int i = 0;
                    	for(World w : Bukkit.getWorlds()){
                    		for(Entity ent : w.getEntities()){
                    			if(isRemovable(ent)){
                    				ent.remove();
                    				i++;
                    			}
                    	}
                    	}
                    	Util.sendMsg(p, "&cEntities was removed, amount of entities: " + i);
                        Util.sendMsg(p, "&cRefresh chunks...");
                    	int ii = 0;
                    	for(World w : Bukkit.getWorlds()){
                    		for(Chunk c : w.getLoadedChunks()){
                    			c.unload();
                    			c.load();
                    			ii++;
                    		}
                    	}
                    	Util.sendMsg(p, "&cChunks was refreshed, amount of chunks: " + ii);
                    	if(isPlugin("ClearLag")){
                            Util.sendMsg(p, "&cEnabling lagg halt...");
                    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg halt");
                            Util.sendMsg(p, "&cLagg halt was enabling...");
                    	}else{
                    		Util.sendMsg(p, "&cClearLag not found");
                    	}
                    }
                }
             }
		}
	}
    private boolean isRemovable(Entity e)
    {
      return ((e instanceof Item)) || ((e instanceof TNTPrimed)) || ((e instanceof ExperienceOrb)) || ((e instanceof FallingBlock)) || ((e instanceof Monster));
    }

}
