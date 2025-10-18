package plugin.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelManageCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("このコマンドはプレイヤー専用です");
      return true;
    }

    if (args.length == 0) {
      player.sendMessage("使い方： /levelup <数字>");
      return true;
    }

    switch (args[0].toLowerCase()) {
      case "max" -> setLevelMax(player);
      case "reset" -> resetLevel(player);
      default -> {
        try {
          int level = Integer.parseInt(args[0]);
          player.setLevel(level);
          player.sendMessage("レベル" + level + "に設定した！！");
        } catch (NumberFormatException ne) {
          player.sendMessage("/levelup <数字>　で入力！入力！！");
        }
      }
    }

    return false;
  }

  public void setLevelMax(Player player) {
    player.setLevel(100);
    player.sendMessage("レベルMax！！🔥");
  }

  public void resetLevel(Player player) {
    player.setLevel(0);

    player.sendMessage("レベルをリセットした...💤");
  }
}
