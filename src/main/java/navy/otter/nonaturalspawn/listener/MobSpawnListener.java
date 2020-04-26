package navy.otter.nonaturalspawn.listener;

import java.util.List;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import navy.otter.nonaturalspawn.config.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnListener implements Listener {

  Configuration config = NoNaturalSpawnPlugin.getConfiguration();
  List<EntityType> entityTypes = config.getEntityList();
  private final boolean whitelistMode = config.isWhitelistMode();
  private final boolean preventIronGolemSpawning = config.isPreventIronGolemSpawning();

  @EventHandler
  public void onMobSpawn(CreatureSpawnEvent e) {
    Entity entity = e.getEntity();
    SpawnReason reason = e.getSpawnReason();

    switch (reason) {
      case NATURAL:
        if (!whitelistMode) {
          if (entityTypes.contains(entity.getType())) {
            e.setCancelled(true);
          }
        } else {
          if (!entityTypes.contains(entity.getType())) {
            e.setCancelled(true);
          }
        }
        break;
      case VILLAGE_DEFENSE:
        if (entity instanceof IronGolem && preventIronGolemSpawning) {
          e.setCancelled(true);
        } else {
          return;
        }
        break;
    }
  }
}
