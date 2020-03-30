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
    private final static String PROHIBITED_CREATURES = "prohibited-creatures";
    private final static String LIST_INFO_MESSAGE = "list-info-message";
  }

  private final boolean isEnabled;
  private List<EntityType> prohibitedEntities;
  private final String listInfoMessage;
  NoNaturalSpawnPlugin noNaturalSpawnPlugin = NoNaturalSpawnPlugin.getInstance();

  public Configuration(@NotNull NoNaturalSpawnPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    this.isEnabled = config.getBoolean(Key.PLUGIN_ENABLED);
    this.prohibitedEntities = new ArrayList<>();
    List<String> entityNames = config.getStringList(Key.PROHIBITED_CREATURES);
    for (String entityName : entityNames) {
      boolean isValid = false;
      for (EntityType entityType : EntityType.values()) {
        if (entityType.name().equalsIgnoreCase(entityName)) {
          isValid = true;
          prohibitedEntities.add(entityType);
          break;
        }
      }
      if (prohibitedEntities.isEmpty()) {
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
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public List<EntityType> getProhibitedEntities() {
    return prohibitedEntities;
  }

  public String getListInfoMessage() {
    return listInfoMessage;
  }
}
