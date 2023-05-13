package fr.robotv2.robotguiframework.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum ExternalSourceItem {

    VANILLA((material -> new ItemStack(Material.getMaterial(material.toUpperCase())))),
    ;

    private final Function<String, ItemStack> function;

    ExternalSourceItem(Function<String, ItemStack> function) {
        this.function = function;
    }

    public ItemStack toItemStack(String value) {
        return function.apply(value);
    }

    private static final Map<String, ExternalSourceItem> BY_NAME = new HashMap<>();

    public static ExternalSourceItem fromName(@NotNull String name) {
        return BY_NAME.get(name.toUpperCase());
    }

    static {
        for(ExternalSourceItem source : values()) {
            BY_NAME.put(source.name(), source);
        }
    }

}
