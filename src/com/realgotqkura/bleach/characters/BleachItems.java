package com.realgotqkura.bleach.characters;

import com.realgotqkura.bleach.main.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BleachItems {


    public static ItemStack regularZanpakuto(){
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.color("&bZanpakuto"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7Damage: &c10"));
        lore.add(Utils.color("&7Reiatsu: &b25"));
        lore.add("");
        lore.add(Utils.color("&e&lRIGHT CLICK &6Block&7: "));
        lore.add(Utils.color("&7Blocks melee attacks and"));
        lore.add(Utils.color("&7negates damage."));
        lore.add("");
        lore.add(Utils.color("&e&lDROP (Q) &6Flash Step&7: "));
        lore.add(Utils.color("&7Dashes at the current direction"));
        lore.add(Utils.color("&7you are moving."));
        lore.add("");
        lore.add(Utils.color("&0Type: Zanpakuto"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack ichigoZangetsu(){
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.color("&bZangetsu"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.color("&7Damage: &c20 sk(40) bk(70)"));
        lore.add(Utils.color("&7Reiatsu: &b25 sk(45) bk(130)"));
        lore.add("");
        lore.add(Utils.color("&8(Shikai minimum)"));
        lore.add(Utils.color("&e&lRIGHT CLICK &6Getsuga Tensho&7: "));
        lore.add(Utils.color("&7Launch a powerful strike that"));
        lore.add(Utils.color("&7goes in 1 direction"));
        lore.add("");
        lore.add(Utils.color("&e&lDROP (Q) &6Flash Step&7: "));
        lore.add(Utils.color("&7Dashes at the current direction"));
        lore.add(Utils.color("&7you are moving."));
        lore.add("");
        lore.add(Utils.color("&0Type: Zanpakuto"));
        lore.add(Utils.color("&0Name: Zangetsu"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack ichigoTransforms(){
        ItemStack stack = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.color("&aTransformations (Default)"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Utils.color("&eAvailable Transformations:"));
        lore.add(Utils.color("&bDefault, Shikai, Bankai"));
        lore.add("");
        lore.add(Utils.color("&e&lRIGHT CLICK &6Transform: "));
        lore.add(Utils.color("&7upgrades your current transformation"));
        lore.add(Utils.color("&7in the order shown above"));
        lore.add("");
        lore.add(Utils.color("&e&lLEFT CLICK &6Degrade: "));
        lore.add(Utils.color("&7Degrades your current transformation"));
        lore.add(Utils.color("&7to the Default one"));
        lore.add("");
        lore.add(Utils.color("&0Char: Ichigo"));
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);
        return stack;
    }
}
