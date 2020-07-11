package navy.otter.nonaturalspawn;

import java.util.logging.Logger;
import navy.otter.nonaturalspawn.command.NoNaturalSpawnCommand;
import navy.otter.nonaturalspawn.command.NoNaturalSpawnCommandTabCompleter;
import navy.otter.nonaturalspawn.config.Configuration;
import navy.otter.nonaturalspawn.listener.MobSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class NoNaturalSpawnPlugin extends JavaPlugin {

  static Configuration config;
  Logger log;
  static NoNaturalSpawnPlugin noNaturalSpawnPlugin;

  @Override
  public void onEnable() {
    noNaturalSpawnPlugin = this;
    config = new Configuration(this);
    log = this.getLogger();

    this.getCommand("nonaturalspawn").setExecutor(new NoNaturalSpawnCommand());
    this.getCommand("nonaturalspawn").setTabCompleter(new NoNaturalSpawnCommandTabCompleter());

    if(config.isEnabled()) {
      getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
    } else {
      Bukkit.getPluginManager().disablePlugin(this);
    }
  }

  public static Configuration getConfiguration() {
    return config;
  }

  public Logger getLog() {
    return log;
  }

  public static NoNaturalSpawnPlugin getInstance() {
    return noNaturalSpawnPlugin;
  }
}
