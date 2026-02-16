# 🔧 BlackWater API

> Utility framework for Bukkit/Spigot plugin development

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Bukkit](https://img.shields.io/badge/Bukkit-1.8+-brightgreen.svg)](https://bukkit.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📖 About

BlackWater API (BWAPI) is a comprehensive utility framework designed to simplify and accelerate Bukkit/Spigot plugin development. It provides ready-to-use implementations of common patterns and utilities, allowing developers to focus on game logic rather than boilerplate code.

## ✨ Features

### 🎮 Command System
Simplified command registration with built-in permission checks and command logging.

```java
public class MyCommand extends Command {
    public MyCommand() {
        super("mycommand", "Description", "/mycommand <args>", "my.permission", "alias1", "alias2");
    }
    
    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        // Your command logic here
        return true;
    }
}

// Register
API.registerCommand(new MyCommand());
```

**Features:**
- Automatic permission validation
- Command logging to database
- Super admin bypass system
- Alias support
- Usage and description metadata

### 📦 Inventory GUI System
Create interactive chest GUIs with action callbacks.

```java
InventoryGUI gui = new InventoryGUI(plugin, "&cMy Menu", 3); // 3 rows

gui.setItem(0, new ItemStack(Material.DIAMOND), (player, inventory, slot, event) -> {
    player.sendMessage("You clicked a diamond!");
    return true;
});

gui.setOpenAction((player, inventory, slot, event) -> {
    player.sendMessage("Menu opened!");
    return true;
});

gui.setCloseAction((player, inventory, slot, event) -> {
    player.sendMessage("Menu closed!");
    return true;
});

gui.openInventory(player);
```

**Features:**
- Click action callbacks per slot
- Open/close event handlers
- Anti-spam with configurable cooldown
- Inventory cloning support
- Automatic event registration

### 💾 MySQL Store System
Simplified database operations with connection pooling and async support.

```java
// Configuration (config.yml)
database:
  mysql:
    host: "localhost"
    port: 3306
    user: "root"
    pass: "password"
    name: "database"
    tableprefix: "bw_"

// Usage
Store store = API.getStore();

// Create table
API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}users` (...)");

// Query
store.query("SELECT * FROM `{P}users`", resultSet -> {
    while (resultSet.next()) {
        String name = resultSet.getString("name");
    }
});

// Update (async)
store.update(false, "UPDATE `{P}users` SET points=10 WHERE uuid='...'");

// Update (sync)
store.update(true, "INSERT INTO `{P}users` VALUES (...)");
```

**Features:**
- Automatic connection management
- Async query support with callbacks
- Table prefix system (`{P}` placeholder)
- Reconnection handling
- Entry interface for data models

### ⏱️ Timer System
Visual countdown timers with title/actionbar display.

```java
TimerUtil.createTimer(player, 10, (timer) -> {
    // Callback when timer completes
    player.sendMessage("Timer finished!");
});
```

**Features:**
- Title-based timer display
- ActionBar support
- Configurable duration
- Completion callbacks
- Automatic cleanup

### 📝 Config System
Simplified YAML configuration management.

```java
public class MyConfig extends ConfigCreator {
    public static String MY_VALUE;
    public static int MY_NUMBER;
    
    public MyConfig() {
        super("config.yml", "MyConfig");
    }
    
    @Override
    public void loadConfig() {
        MY_VALUE = getString("my.value", "default");
        MY_NUMBER = getInt("my.number", 100);
    }
}

// Register
API.registerConfig(new MyConfig());
```

**Features:**
- Automatic file creation
- Default value support
- Type-safe getters
- Hot reload support
- Multiple config files

### 🧰 Utility Classes

#### ItemBuilder
```java
ItemStack item = new ItemBuilder(Material.DIAMOND_SWORD)
    .setTitle("&cMy Sword")
    .addLore("&7Line 1", "&7Line 2")
    .addEnchantment(Enchantment.DAMAGE_ALL, 5)
    .setAmount(1)
    .build();
```

#### TitleUtil
```java
TitleUtil.sendTitle(player, 10, 60, 10, "&cMain Title", "&7Subtitle");
TitleUtil.sendFullTitle(player, "&cTitle", "&7Subtitle");
```

#### ActionBarUtil
```java
ActionBarUtil.sendActionbar(player, "&aHealth: " + player.getHealth());
```

#### ItemSerializer
```java
// Serialize
String serialized = ItemSerializer.itemStackToBase64(itemStack);

// Deserialize
ItemStack item = ItemSerializer.itemStackFromBase64(serialized);
```

#### Base64Util
```java
// Serialize inventory
String encoded = Base64Util.itemStackArrayToBase64(player.getInventory().getContents());

// Deserialize inventory
ItemStack[] items = Base64Util.itemStackArrayFromBase64(encoded);
```

#### Reflection Utilities
```java
// Get NMS version
String nmsVer = API.nmsver; // "v1_8_R3"

// Send packet
ReflectionUtil.sendPacket(player, packet);

// Get field value
Object value = Reflection.getField(object, "fieldName");
```

#### TimeUtil
```java
long time = TimeUtil.SECOND.getTime(30); // 30 seconds in milliseconds
long ticks = TimeUtil.MINUTE.getTick(5); // 5 minutes in ticks
String formatted = TimeUtil.getDate(timestamp); // "2024-01-15 14:30:00"
```

#### MathUtil
```java
double rounded = MathUtil.round(3.14159, 2); // 3.14
int random = MathUtil.getRandInt(1, 100); // Random between 1-100
```

#### SpaceUtil
```java
String centered = SpaceUtil.getCenteredMessage("&cWelcome!");
```

### 🔌 Socket System
WebSocket support for external communication.

```java
// Configuration
SocketConfiguration config = new SocketConfiguration(
    "ws://localhost:8080",
    "reconnectToken"
);

// Task scheduling
SocketTask socketTask = API.getSocketTask();
```

### 🧵 Incognito Thread System
Background task execution independent of main thread.

```java
IncognitoAction action = new IncognitoAction(
    IncognitoActionType.EXECUTE,
    () -> {
        // Background task
    }
);

IndependentThread.getActions().add(action);
```

## 🚀 Getting Started

### Installation

1. **Add BWAPI as dependency:**
```xml
<!-- Maven -->
<dependency>
    <groupId>pl.blackwater</groupId>
    <artifactId>bwapi</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. **Include BWAPI jar in your plugin:**
   - Add `BWAPI.jar` to your server's plugins folder
   - Add dependency in `plugin.yml`:
```yaml
depend: [BWAPI]
```

### Configuration

Create `plugins/BWAPI/config.yml`:

```yaml
database:
  mysql:
    host: "localhost"
    port: 3306
    user: "root"
    pass: "password"
    name: "minecraft"
    tableprefix: "bw_"

superadmin:
  system:
    adminuuid:
      - "uuid-here"
```

### Basic Usage

```java
public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Register command
        API.registerCommand(new MyCommand());
        
        // Register listener
        API.registerListener(this, new MyListener());
        
        // Register config
        API.registerConfig(new MyConfig());
        
        // Access database
        Store store = API.getStore();
        API.addMYSQLTable("CREATE TABLE IF NOT EXISTS `{P}mytable` (...)");
    }
}
```

## 📚 API Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `API` | Main plugin class and registry |
| `Command` | Base command class |
| `InventoryGUI` | GUI inventory builder |
| `Store` | Database interface |
| `ConfigCreator` | Config base class |
| `Entry` | Database entity interface |

### Utilities

| Class | Purpose |
|-------|---------|
| `ItemBuilder` | ItemStack builder |
| `TitleUtil` | Title/subtitle sender |
| `ActionBarUtil` | ActionBar sender |
| `Base64Util` | Inventory serialization |
| `ItemSerializer` | Item serialization |
| `Reflection` | NMS reflection |
| `TimeUtil` | Time formatting |
| `MathUtil` | Math operations |
| `Util` | General utilities |

## 🔧 Advanced Features

### Entry Interface
Implement `Entry` for auto-save entities:

```java
public class User implements Entry {
    private UUID uuid;
    private String name;
    private int points;
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `{P}users` VALUES (...)";
        API.getStore().update(true, sql);
    }
    
    @Override
    public void update(boolean now) {
        String sql = "UPDATE `{P}users` SET ... WHERE uuid='" + uuid + "'";
        API.getStore().update(now, sql);
    }
    
    @Override
    public void delete() {
        String sql = "DELETE FROM `{P}users` WHERE uuid='" + uuid + "'";
        API.getStore().update(true, sql);
    }
}
```

### Command Logging
All commands are automatically logged to database when player has `api.commandlog` permission:

```sql
CREATE TABLE `{P}commandslog` (
    id INT AUTO_INCREMENT,
    command TEXT,
    executor VARCHAR(16),
    time BIGINT,
    PRIMARY KEY (id)
);
```

### Custom Ticking System
BWAPI includes a custom tick system for precise timing:

```java
Ticking ticking = new Ticking();
ticking.start();
```

## 📦 Package Structure

```
pl.blackwaterapi/
├── API.java                    # Main class
├── commands/                   # Command system
│   ├── Command.java
│   ├── CommandManager.java
│   └── PlayerCommand.java
├── configs/                    # Config system
│   ├── ConfigCreator.java
│   └── ConfigManager.java
├── gui/                        # GUI system
│   ├── actions/
│   │   ├── InventoryGUI.java
│   │   └── IAction.java
│   └── listeners/
│       └── InventoryListener.java
├── store/                      # Database
│   ├── Store.java
│   ├── Entry.java
│   ├── Callback.java
│   └── modes/
│       └── StoreMySQL.java
├── timer/                      # Timer system
│   ├── TimerUtil.java
│   └── TimerManager.java
├── sockets/                    # WebSocket
│   ├── SocketTask.java
│   └── SocketConfiguration.java
├── incognitothreads/          # Background tasks
│   ├── IndependentThread.java
│   └── IncognitoAction.java
└── utils/                      # Utilities
    ├── ItemBuilder.java
    ├── TitleUtil.java
    ├── ActionBarUtil.java
    ├── Base64Util.java
    ├── Reflection.java
    ├── TimeUtil.java
    └── ...
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**BlackWater Development**
- Package: `pl.blackwaterapi`

---

**Built with ❤️ for the Bukkit/Spigot community**
