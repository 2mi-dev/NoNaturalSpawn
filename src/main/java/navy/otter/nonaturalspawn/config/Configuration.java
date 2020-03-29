package navy.otter.nonaturalspawn.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.extern.slf4j.Slf4j;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class Configuration {

  private static class Key {

    private final static String PLUGIN_ENABLED = "plugin-enabled";
    private final static String PERMITTED_CREATURES = "permitted-creatures";
  }

  private final boolean isEnabled;
  private List<EntityType> entities;
  NoNaturalSpawnPlugin noNaturalSpawnPlugin = NoNaturalSpawnPlugin.getInstance();

  public Configuration(@NotNull NoNaturalSpawnPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    this.isEnabled = config.getBoolean(Key.PLUGIN_ENABLED);
    this.entities = new ArrayList<>();
    List<String> entityNames = config.getStringList(Key.PERMITTED_CREATURES);
    for (String entityName : entityNames) {
      //EntityType entityType = EntityType.valueOf(entityName.toLowerCase());
      for (EntityType entityType : EntityType.values()) {
        if (entityType.name().equalsIgnoreCase(entityName)) {
          entities.add(entityType);
          break;
        }
      }
      if (entities.isEmpty()) {
        Bukkit.getPluginManager().disablePlugin(noNaturalSpawnPlugin);
        noNaturalSpawnPlugin.getLogger()
            .log(Level.SEVERE, "One or more Entities are invalid. Disabling NoSpawn.");
      }
    }
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public List<EntityType> getEntities() {
    return entities;
  }
}