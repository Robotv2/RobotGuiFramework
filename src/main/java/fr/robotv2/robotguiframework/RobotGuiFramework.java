package fr.robotv2.robotguiframework;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public final class RobotGuiFramework implements Listener {

    public static void instantiate(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new RobotGuiFramework(), plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        final InventoryHolder holder = event.getClickedInventory().getHolder();

        if(!(holder instanceof IGui)) {
            return;
        }

        ((IGui) holder).handleClick(event);
    }
}
