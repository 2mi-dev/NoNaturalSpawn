package navy.otter.nonaturalspawn.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoNaturalSpawnCommandTabCompleter implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender player,
      @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    ArrayList<String> list = new ArrayList<>();
    if (!(player instanceof Player)) {
      return new ArrayList<>();
    }

    if (args.length <= 1) {
      if (player.hasPermission("nonaturalspawn.list")) {
        list.add("list");

        return list
            .stream()
            .filter((string) -> string.startsWith(args[0]))
            .collect(Collectors.toList());
      }

    }
    return new ArrayList<>();
  }
}
