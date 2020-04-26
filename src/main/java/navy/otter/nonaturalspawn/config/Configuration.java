package navy.otter.nonaturalspawn.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class Configuration {

  private static class Key {
    private final static String PLUGIN_ENABLED = "plugin-enabled";
    private final static String WHITELIST_MODE = "whitelist-mode";
    private final static String CREATURES = "creatures";
    private final static String LIST_INFO_MESSAGE = "list-info-message";
    private final static String PREVENT_IRON_GOLEM_SPAWNING = "prevent-iron-golem-spawning";
  }

  private final boolean isEnabled;
  private final boolean whitelistMode;
  private List<EntityType> entityList;
  private final String listInfoMessage;
  private final boolean preventIronGolemSpawning;
  NoNaturalSpawnPlugin noNaturalSpawnPlugin = NoNaturalSpawnPlugin.getInstance();

  public Configuration(@NotNull NoNaturalSpawnPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    this.isEnabled = config.getBoolean(Key.PLUGIN_ENABLED);
    this.whitelistMode = config.getBoolean(Key.WHITELIST_MODE);
    this.entityList = new ArrayList<>();
    List<String> entityNames = config.getStringList(Key.CREATURES);
    for (String entityName : entityNames) {
      boolean isValid = false;
      for (EntityType entityType : EntityType.values()) {
        if (entityType.name().equalsIgnoreCase(entityName)) {
          isValid = true;
          entityList.add(entityType);
          break;
        }
      }
      if (entityList.isEmpty()) {
        noNaturalSpawnPlugin.getLogger()
            .log(Level.SEVERE, "Either no entities were set in config or all are invalid. Disabling NoSpawn.");
        Bukkit.getPluginManager().disablePlugin(noNaturalSpawnPlugin);
      } else if (!isValid) {
        noNaturalSpawnPlugin.getLogger()
            .log(Level.SEVERE, "Some entities in config are invalid. Disabling NoSpawn.");
        Bukkit.getPluginManager().disablePlugin(noNaturalSpawnPlugin);
      }
    }
    this.listInfoMessage = config.getString(Key.LIST_INFO_MESSAGE);
    this.preventIronGolemSpawning = config.getBoolean(Key.PREVENT_IRON_GOLEM_SPAWNING);
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public boolean isWhitelistMode() {
    return whitelistMode;
  }

  public List<EntityType> getEntityList() {
    return entityList;
  }

  public String getListInfoMessage() {
    return listInfoMessage;
  }

  public boolean isPreventIronGolemSpawning() {
    return preventIronGolemSpawning;
  }
}
