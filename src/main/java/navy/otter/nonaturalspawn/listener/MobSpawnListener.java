package navy.otter.nonaturalspawn.listener;

import java.util.List;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import navy.otter.nonaturalspawn.config.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnListener implements Listener {

  Configuration config = NoNaturalSpawnPlugin.getConfiguration();
  List<EntityType> entityTypes = config.getEntityList();
  private final boolean whitelistMode = config.isWhitelistMode();

  @EventHandler
  public void onMobSpawn(CreatureSpawnEvent e) {
    Entity entity = e.getEntity();
    SpawnReason reason = e.getSpawnReason();

    if(!reason.equals(SpawnReason.NATURAL)) {
      return;
    }

    if (!whitelistMode) {
      if (entityTypes.contains(entity.getType())) {
        e.setCancelled(true);
      }
    } else {
      if (!entityTypes.contains(entity.getType())) {
        e.setCancelled(true);
      }
    }
  }
}
