package com.realgotqkura.bleach.main;

import com.realgotqkura.bleach.characters.BleachItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final main plugin;
    public Commands(main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only for players");
            return true;
        }

        Player player = (Player) sender;
        if(label.equalsIgnoreCase("bleachCharacter")){
            if(args.length == 0){
                player.sendMessage(Utils.color("&cSpecify a character"));
                return true;
            }
            if(args[0].equalsIgnoreCase("Default")){
                player.sendMessage(Utils.color("&aSuccessfully gave the Default character."));
                player.getInventory().addItem(BleachItems.regularZanpakuto());
                return true;
            }else if(args[0].equalsIgnoreCase("Ichigo")){
                plugin.getConfig().set(player.getUniqueId().toString() + ".character", 1); // 1 = Ichigo
                plugin.saveConfig();
                player.sendMessage(Utils.color("&aSuccessfully gave the Ichigo character."));
                player.getInventory().addItem(BleachItems.ichigoZangetsu());
                player.getInventory().addItem(BleachItems.ichigoTransforms());
                return true;
            }
        }
        return false;
    }
}
