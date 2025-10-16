package plugin.sample;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count;
  BigInteger val = new BigInteger("1");

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();
    BigInteger val = BigInteger.valueOf(count);
    List<Color> colorList = List.of(Color.RED, Color.BLUE, Color.LIME, Color.BLACK);

    if (val.isProbablePrime(50)) {
      for (Color color : colorList) {

        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder().withColor(color).with(Type.STAR).withFlicker().build());
        fireworkMeta.setPower(0);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
      }
      player.sendMessage("素敵な花火だ！　count = " + count);
      Path path = Path.of("fireWorks");
      Files.writeString(path, "たーまやー");
      player.sendMessage(Files.readString(path));
    }

    count++;


  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof Firework) {
      e.setCancelled(true); // ← ダメージを0にして完全無効化
    }
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    Arrays.stream(itemStacks).filter(
            item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64)
        .forEach(item -> item.setAmount(64));
    player.getInventory().setContents(itemStacks);

  }

  @EventHandler
  public void onHitAnimal(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      player.sendMessage("動物を殴りましたね！！ひどい！！そんなことするなんて！");
      String[] colors = {"§a", "§b", "§c", "§d", "§e"};
      Arrays.stream(colors).forEach(color -> {
        String message = "無駄な殺生はしないでね";
        player.sendMessage(color + message);
      });
    }

  }
}
