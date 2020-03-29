package navy.otter.nonaturalspawn.listener;

import java.util.List;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnListener implements Listener {

  List<EntityType> entityTypes = NoNaturalSpawnPlugin.getConfiguration().getProhibitedEntities();

  @EventHandler
  public void onMobSpawn(CreatureSpawnEvent e) {
    Entity entity = e.getEntity();
    SpawnReason reason = e.getSpawnReason();

    if(entityTypes.contains(entity.getType()) && reason.equals(SpawnReason.NATURAL)) {
      e.setCancelled(true);
    }
  }
}
