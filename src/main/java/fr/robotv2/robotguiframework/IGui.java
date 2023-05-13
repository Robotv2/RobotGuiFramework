package fr.robotv2.robotguiframework;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class IGui implements InventoryHolder {

    protected final String name;
    protected final int row;

    public IGui(int row, String name) {
        row = Math.min(6, row); // row as to be max. 6
        row = Math.max(1, row); // row cannot be less than 1.
        this.name = name;
        this.row = row;
    }

    @Override
    public Inventory getInventory() {
        return null; // For now, only solution I could find. Maybe better alternative :) ?
    }

    public String getName() {
        return this.name;
    }

    public int getRow() {
        return this.row;
    }

    public abstract void populateItems(Inventory inventory, Player player);
    public abstract void show(Player player);
    abstract void handleClick(InventoryClickEvent event);

}
