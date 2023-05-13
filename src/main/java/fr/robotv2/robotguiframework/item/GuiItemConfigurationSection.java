package fr.robotv2.robotguiframework.item;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuiItemConfigurationSection {

    private final ConfigurationSection section;
    private final ExternalSourceItem source;

    public GuiItemConfigurationSection(ConfigurationSection section) {
        this.section = section;
        this.source = ExternalSourceItem.fromName(section.getString("source", "VANILLA"));
    }

    public String getMaterialValue() {
        return this.section.getString("material-value");
    }

    public String getName() {
        return this.section.getString("name");
    }

    public List<String> getLore() {
        return this.section.getStringList("lore");
    }

    public int getCustomModelData() {
        return this.section.getInt("custom-model-data");
    }

    public boolean isGlowing() {
        return this.section.getBoolean("glowing");
    }

    public Set<ItemFlag> getFlags() {
        return this.section.getStringList("item-flags").stream()
                .map(String::toUpperCase)
                .map(ItemFlag::valueOf)
                .collect(Collectors.toSet());
    }

    public GuiItem toGuiItem() {

        final String materialValue = this.getMaterialValue();
        final GuiItem item = new GuiItem(this.source.toItemStack(materialValue));

        if(this.getName() != null) {
            item.name(this.getName());
        }

        if(!this.getLore().isEmpty()) {
            item.lore(this.getLore());
        }

        if(this.isGlowing()) {
            item.enchant(Enchantment.ARROW_DAMAGE);
            item.flags(ItemFlag.HIDE_ENCHANTS);
        }

        for(ItemFlag flag : this.getFlags()) {
            item.flags(flag);
        }

        if(this.getCustomModelData() != 0) {
            item.data(this.getCustomModelData());
        }

        return item;
    }
}
