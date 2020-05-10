package pl.blackwaterapi;


import lombok.Getter;
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
import pl.blackwaterapi.data.APIConfig;
import pl.blackwaterapi.data.CommandLogStorage;
import pl.blackwaterapi.gui.listeners.InventoryListener;
import pl.blackwaterapi.incognitothreads.IndependentThread;
import pl.blackwaterapi.sockets.SocketTask;
import pl.blackwaterapi.store.Store;
import pl.blackwaterapi.store.modes.StoreMySQL;
import pl.blackwaterapi.timer.TimerManager;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Ticking;

import java.util.ArrayList;
import java.util.List;

public class API extends JavaPlugin
{
    @SuppressWarnings("unused")
	private static CommandMap cmdMap;
    private static API plugin;
    private static Store store;
    @Getter
    private static PluginManager pluginManager;
    private static SocketTask socketTask;
    public static API instance;
    public static String nmsver;
    @Getter
    private static APIConfig apiConfig;
    @Getter
    private static CommandLogStorage commandLogStorage;
    @Getter
    private static List<Listener> listeners = new ArrayList<>();
    public void onLoad()
    {
      plugin = this;
    }
    
    public void onEnable() {
        new IndependentThread().start();
        Logger.info("BWAPI - Registering nmsver...");
        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        Logger.info("BWAPI - Nmsver registered, " + nmsver);

        Logger.info("BWAPI - Ticking infection - start");
        new Ticking().start();
        Logger.info("BWAPI - Start ticking !");

        Logger.info("BWAPI - Registering configs...");
        registerConfig( apiConfig = new APIConfig());
        registerConfig(commandLogStorage = new CommandLogStorage());
        Logger.info("BWAPI - Configs registered");

        Logger.info("BWAPI - Registering MYSQL database...");
        this.registerDatabase();
        Logger.info("BWAPI - MYSQL database registered");

        Logger.info("BWAPI - Registering commands...");
        registerCommand(new APICommand());
        Logger.info("BWAPI - Command registered");

        Logger.info("BWAPI - Registering listeners...");
        registerListener(this, new TimerManager(), new InventoryListener());
        Logger.info("BWAPI - Listeners registered");

    }
    
    public void onDisable() {
        Logger.info("BWAPI - Disabling API system...");
        try {
            Thread.sleep(2000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (API.store != null && API.store.isConnected()) {
            API.store.disconnect();
        }
        Logger.info("BWAPI - API system disabled");
    }
    protected boolean registerDatabase() {
        API.store = new StoreMySQL(APIConfig.DATABASE_MYSQL_HOST, APIConfig.DATABASE_MYSQL_PORT, APIConfig.DATABASE_MYSQL_USER, APIConfig.DATABASE_MYSQL_PASS, APIConfig.DATABASE_MYSQL_NAME, APIConfig.DATABASE_TABLEPREFIX);
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
            getListeners().add(listener);
            API.pluginManager.registerEvents(listener, plugin);
        }
    }
    public static void error(String content) {
        Bukkit.getLogger().severe("[Server thread/ERROR] #!# " + content);
    }
    public static boolean exception(String cause, StackTraceElement[] ste) {
        error("");
        error("[NanoIncognito] Severe error:");
        error("");
        error("Server Information:");
        error("  Bukkit: " + Bukkit.getBukkitVersion());
        error("  Java: " + System.getProperty("java.version"));
        error("  Thread: " + Thread.currentThread());
        error("  Running CraftBukkit: " + Bukkit.getServer().getClass().getName().equals("org.bukkit.craftbukkit.CraftServer"));
        error("");
        if (cause == null || ste == null || ste.length < 1) {
            error("Stack trace: no/empty exception given, dumping current stack trace instead!");
            return true;
        }
        else {
            error("Stack trace: ");
        }
        error("Caused by: " + cause);
        for (StackTraceElement st : ste) {
            error("    at " + st.toString());
        }
        error("");
        error("End of Error.");
        error("");
        return false;
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
    
    
    public static SocketTask getSocketTask() {
        return API.socketTask;
    }
}

