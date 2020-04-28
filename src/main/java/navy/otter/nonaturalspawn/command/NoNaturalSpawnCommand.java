package navy.otter.nonaturalspawn.command;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import navy.otter.nonaturalspawn.NoNaturalSpawnPlugin;
import navy.otter.nonaturalspawn.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NoNaturalSpawnCommand implements CommandExecutor {

  Configuration config = NoNaturalSpawnPlugin.getConfiguration();

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
      @NotNull String s, @NotNull String[] strings) {
    if (!(commandSender instanceof Player)) {
      return false;
    }

    Player player = (Player) commandSender;
    Iterator<String> arg = Arrays.asList(strings).iterator();
    String option = arg.hasNext() ? arg.next() : "";

    if (option.toLowerCase().equals("list") && player.hasPermission("nonaturalspawn.list")) {
      displayList(player);
    }

    return false;
  }

  public void displayList(Player player) {

    List<EntityType> prohibitedEntityTypeList = NoNaturalSpawnPlugin.getConfiguration().getEntityList();
    StringBuilder sb =  new StringBuilder();
    String golemStatus = "ON";


    if(config.isPreventIronGolemSpawning()) {
      golemStatus = "OFF";
    }

    for(EntityType entityType : prohibitedEntityTypeList) {
      sb.append(entityType.toString());
      sb.append(", ");
    }

    String mode = "BLACKLIST: ";
    if(config.isWhitelistMode()) {
      mode = "WHITELIST: ";
    }

    player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "NoNaturalSpawn"
        + ChatColor.DARK_GRAY +"] " + ChatColor.GRAY +  config.getListInfoMessage());
    player.sendMessage(ChatColor.GRAY + mode + sb.toString() + "IRON_GOLEM: " + golemStatus);
  }
}
