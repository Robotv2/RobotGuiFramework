package fr.robotv2.robotguiframework;

import fr.robotv2.robotguiframework.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Inspired by the FastInv Inventory API.
 * <a href="https://github.com/MrMicky-FR/FastInv">...</a>
 */

public class NormalGui extends IGui {

    private Consumer<InventoryClickEvent> generalClickEvent;
    protected final Map<Integer, GuiItem> items = new HashMap<>();

    public NormalGui() {
        this(3, "Default Title");
    }

    public NormalGui(int row) {
        this(row, "Default Title");
    }

    public NormalGui(int row, String name) {
        super(row, name);
    }

    @Override
    public void populateItems(Inventory inventory, Player player) {
        this.items.forEach((slot, item) -> inventory.setItem(slot, item.getCopyFor(player)));
    }

    @Override
    public void show(Player player) {
        final Inventory inventory = Bukkit.createInventory(this, row * 9, name);
        this.populateItems(inventory, player);
        player.openInventory(inventory);
    }

    @Override
    void handleClick(InventoryClickEvent event) {

        if(getGeneralClickEvent() != null) {
            getGeneralClickEvent().accept(event);
        }

        final GuiItem item = this.items.get(event.getRawSlot());

        if(item != null && item.getClickConsumer() != null) {
            item.getClickConsumer().accept(event);
        }
    }

    public void setItem(GuiItem item, int slot) {
        this.items.put(slot, item);
    }

    public void setItems(GuiItem item, int... slots) {
        for(int slot : slots) {
            setItem(item, slot);
        }
    }

    public void setItems(GuiItem item, int from, int to) {
        setItems(item, IntStream.range(from, to + 1).toArray());
    }

    public void fill(GuiItem item) {
        for(int i = 0; i < 9 * row; i++) {
            setItem(item, i);
        }
    }

    public void fillEmpty(GuiItem item) {
        for(int i = 0; i <  9 * row; i++) {
            final GuiItem current = this.items.get(i);
            if(current == null) {
                setItem(item, i);
            }
        }
    }

    public Consumer<InventoryClickEvent> getGeneralClickEvent() {
        return generalClickEvent;
    }

    public void setGeneralClickEvent(Consumer<InventoryClickEvent> consumer) {
        this.generalClickEvent = consumer;
    }
}
