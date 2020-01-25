package pl.blackwaterapi;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.blackwaterapi.commands.APICommand;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.commands.CommandManager;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.configs.ConfigManager;
import pl.blackwaterapi.data.Config;
import pl.blackwaterapi.sockets.SocketTask;
import pl.blackwaterapi.store.Store;
import pl.blackwaterapi.store.StoreMode;
import pl.blackwaterapi.store.modes.StoreMySQL;
import pl.blackwaterapi.tasks.TPSLiveTimeTask;
import pl.blackwaterapi.timer.TimerManager;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Ticking;
import pl.blackwaterapi.utils.TimeUtil;

public class API extends JavaPlugin
{
    @SuppressWarnings("unused")
	private static CommandMap cmdMap;
    private static API plugin;
    private static Store store;
    private static PluginManager pluginManager;
    private static SocketTask socketTask;
    public static API instance;
    public static boolean works = true;
    public static String nmsver;
    public static boolean useOldMethods = false;
    public void onLoad()
    {
      plugin = this;
    }
    
    public void onEnable() {
        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
            useOldMethods = true;
            Logger.info("useOldMethods switch on");
        }
        Logger.info("Register nmsver: " + nmsver);
        new Ticking().start();
        Logger.info("Start ticking !");
        Config.reloadConfig();
        Logger.info("Register DataBase Config");
        this.registerDatabase();
        Logger.info("Register Database");
        registerListener(this, new TimerManager(), new APICommand());
        registerCommand(new APICommand());
        new TPSLiveTimeTask().runTaskTimerAsynchronously(getPlugin(), 20L, TimeUtil.SECOND.getTick(2));
    }
    
    public void onDisable() {
        try {
            Thread.sleep(2000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (API.store != null && API.store.isConnected()) {
            API.store.disconnect();
        }
    }
    protected boolean registerDatabase() {
        if (StoreMode.getByName(Config.DATABASE_MODE) == StoreMode.MYSQL) {
            API.store = new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
        } else {
            Logger.warning("Value of databse mode is not valid! Using MYSQL as database!");
            API.store = new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
        }
        return API.store.connect();
    }
    public static void addMYSQLTable(String tableBuild){
        	store.update(true,tableBuild);
    }
    
    public static void registerListener(Plugin plugin, Listener... listeners) {
        if (API.pluginManager == null) {
            API.pluginManager = Bukkit.getPluginManager();
        }
        for (Listener listener : listeners) {
            API.pluginManager.registerEvents(listener, plugin);
        }
    }
    
    public static void registerCommand(Command command) {
        CommandManager.register(command);
    }
    public static void registerConfig(ConfigCreator config){
    	ConfigManager.register(config);
    }
    
    public static API getPlugin() {
        return API.plugin;
    }  
    public static Store getStore() {
        return API.store;
    }
    
    public static PluginManager getPluginManager() {
        return API.pluginManager;
    }
    
    
    public static SocketTask getSocketTask() {
        return API.socketTask;
    }
}

