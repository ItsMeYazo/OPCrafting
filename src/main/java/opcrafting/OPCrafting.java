package opcrafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class OPCrafting extends JavaPlugin implements Listener {
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    getLogger().info("OPCrafting enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("OPCrafting disabled.");
  }

  @EventHandler
  public void onCraft(CraftItemEvent event) {
    if (!(event.getWhoClicked() instanceof Player)) return;
    Player player = (Player) event.getWhoClicked();
    ItemStack crafted = event.getCurrentItem();
    if (crafted == null || crafted.getType() == Material.AIR) return;
    Bukkit.getScheduler().runTask(this, () -> {
      ItemStack op = crafted.clone();
      for (Enchantment e : Enchantment.values()) {
        if (e.canEnchantItem(op)) op.addUnsafeEnchantment(e, 255);
      }
      player.getInventory().addItem(op);
      player.sendMessage("§aCrafted OP item added to inventory.");
    });
  }
}
