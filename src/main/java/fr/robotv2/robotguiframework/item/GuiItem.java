package fr.robotv2.robotguiframework.item;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GuiItem extends ItemStack {

    private Consumer<InventoryClickEvent> clickConsumer;

    public static GuiItem fromConfigurationSection(ConfigurationSection section) {
        return new GuiItemConfigurationSection(section).toGuiItem();
    }

    public GuiItem(Material material) {
        super(material);
    }

    public GuiItem(Material material, int amount) {
        super(material, amount);
    }

    public GuiItem(ItemStack itemStack) {
        super(itemStack.getType(), itemStack.getAmount());
        this.setItemMeta(itemStack.getItemMeta().clone());
    }

    public void editItem(Consumer<ItemStack> stackConsumer) {
        stackConsumer.accept(this);
    }

    public void editMeta(Consumer<ItemMeta> metaConsumer) {
        editItem(item -> {
            final ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                metaConsumer.accept(meta);
                item.setItemMeta(meta);
            }
        });
    }

    public void type(Material material) {
        editItem(item -> item.setType(material));
    }

    public void data(int data) {
        editItem(item -> item.setDurability((short) data));
    }

    public void amount(int amount) {
        editItem(item -> item.setAmount(amount));
    }

    public void enchant(Enchantment enchantment) {
        enchant(enchantment, 1);
    }

    public void enchant(Enchantment enchantment, int level) {
        editMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public void name(String name) {
        editMeta(meta -> meta.setDisplayName(name));
    }

    public void lore(String lore) {
        lore(Collections.singletonList(lore));
    }

    public void lore(String... lore) {
        lore(Arrays.asList(lore));
    }

    public void lore(List<String> lore) {
        editMeta(meta -> meta.setLore(lore));
    }

    public void addLore(String line) {
        editMeta(meta -> {
            List<String> lore = meta.getLore();

            if (lore == null) {
                meta.setLore(Collections.singletonList(line));
                return;
            }

            lore.add(line);
            meta.setLore(lore);
        });
    }

    public void addLore(String... lines) {
        addLore(Arrays.asList(lines));
    }

    public void addLore(List<String> lines) {
        editMeta(meta -> {
            List<String> lore = meta.getLore();

            if (lore == null) {
                meta.setLore(lines);
                return;
            }

            lore.addAll(lines);
            meta.setLore(lore);
        });
    }

    public void flags(ItemFlag... flags) {
        editMeta(meta -> meta.addItemFlags(flags));
    }

    public GuiItem getCopyFor(Player player) {
        return this; // TODO Need to be parsed with placeholder and stuff.
    }

    @Nullable
    public Consumer<InventoryClickEvent> getClickConsumer() {
        return this.clickConsumer;
    }

    public void setClickConsumer(Consumer<InventoryClickEvent> consumer) {
        this.clickConsumer = consumer;
    }
}
