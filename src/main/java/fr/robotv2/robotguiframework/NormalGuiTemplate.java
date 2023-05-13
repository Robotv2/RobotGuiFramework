package fr.robotv2.robotguiframework;

import fr.robotv2.robotguiframework.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalGuiTemplate extends NormalGui {

    private final List<String> rows;
    private final Map<Character, GuiItem> templateItems = new HashMap<>();

    public NormalGuiTemplate(String name, List<String> rows) {
        super(Math.min(rows.size(), 6), name);
        this.rows = rows.size() > 6 ? rows.subList(0, 7) : rows;
    }

    public void addTemplateItem(Character character, GuiItem item) {
        this.templateItems.put(character, item);
    }

    @Override
    public void show(Player player) {

        final Inventory inventory = Bukkit.createInventory(this, row * 9, name);
        int slot = 0;

        for (String line : rows) {
            for (int j = 0; j < 10; j++) {
                try {
                    final Character character = line.charAt(j);
                    final GuiItem item = templateItems.get(character);
                    if(item != null) inventory.setItem(slot, item.getCopyFor(player));
                } catch (StringIndexOutOfBoundsException ignored) {}
                ++slot;
            }
        }

        super.populateItems(inventory, player);
        player.openInventory(inventory);
    }
}
