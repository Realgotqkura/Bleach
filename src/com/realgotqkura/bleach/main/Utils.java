package com.realgotqkura.bleach.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Utils {

    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void multipleTitles(Player player, main plugin, List<String> mains, int i, int i1, int i2){
        int timer = i+i1+i2;
        new BukkitRunnable(){

            int count = 0;
            @Override
            public void run() {
                if(count >= mains.size()){
                    cancel();
                }
                player.sendTitle(mains.get(count), "", i,i1,i2);
                count++;
            }


        }.runTaskTimer(plugin, 1, timer);
    }
}
