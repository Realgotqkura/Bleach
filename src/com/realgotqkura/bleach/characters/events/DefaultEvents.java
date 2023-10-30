package com.realgotqkura.bleach.characters.events;

import com.realgotqkura.bleach.characters.BleachItems;
import com.realgotqkura.bleach.main.Utils;
import com.realgotqkura.bleach.main.main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultEvents implements Listener {


    private final main plugin;
    public DefaultEvents(main plugin){
        this.plugin = plugin;
    }
    public static HashMap<Player, Vector> movementAngles = new HashMap<>();
    public static HashMap<Player, Boolean> freedom = new HashMap<>();


    @EventHandler
    public void move(PlayerMoveEvent event){
        try{
            if(!freedom.get(event.getPlayer())) {
                event.setTo(event.getFrom());
                return;
            }
        }catch(NullPointerException e){
            freedom.put(event.getPlayer(), true);
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            Vector angle = event.getFrom().subtract(event.getTo()).toVector().normalize();
            movementAngles.put(player,angle);
            freedom.put(event.getPlayer(), true);
        }


    }

    @EventHandler
    public void e(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!freedom.get(event.getPlayer())) {
            event.setCancelled(true);
        }
        //Ichigo
        if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(Utils.color("&0Name: Zangetsu"))){
            if(event.getAction() == Action.RIGHT_CLICK_AIR){
                if(plugin.getConfig().getInt(player.getUniqueId().toString() + ".character") == 1){
                    if(plugin.getConfig().getInt(player.getUniqueId().toString() + ".transform") > 0){
                        World world = player.getWorld();
                        WitherSkull wSkull = player.launchProjectile(WitherSkull.class);
                        wSkull.setCharged(true);
                        wSkull.setMetadata("Getsuga", new FixedMetadataValue(JavaPlugin.getPlugin(main.class), 1));
                        player.sendTitle(Utils.color("&f&kEE&bGETSUGA&f&kEE"), "", 5, 10,5);
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                player.sendTitle(Utils.color("&f&kEE&bTENSHO&f&kEE"), "", 5, 10,5);
                                cancel();
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                wSkull.setVelocity(wSkull.getVelocity().multiply(1.35f));
                                for(int i = 0; i < 25; i++){
                                    float randomX = ThreadLocalRandom.current().nextInt(- 100, 100 + 1) / 130f;
                                    float randomZ = ThreadLocalRandom.current().nextInt(- 100, 100 + 1) / 130f;
                                    float randomY = ThreadLocalRandom.current().nextInt(- 300, 300 + 1) / 100f;
                                    world.spawnParticle(Particle.REDSTONE, wSkull.getLocation().getX() + randomX, wSkull.getLocation().getY() + randomY, wSkull.getLocation().getZ() + randomZ, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(78, 223, 243),5));
                                }
                                if(wSkull.isDead()){
                                    cancel();
                                }
                            }

                        }.runTaskTimer(plugin,1,1);
                    }else{
                        player.sendMessage(Utils.color("&cYou need at least Shikai to use this ability!"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void ee(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(!freedom.get(event.getPlayer())) {
            event.setCancelled(true);
        }

        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getItemMeta().getLore().contains(Utils.color("&0Type: Zanpakuto"))){
            event.setCancelled(true);
            player.sendMessage(Utils.color("&7[&e" + player.getName() + "&7]: &bFlash Step!"));
            player.setVelocity(movementAngles.get(player).setY(0).multiply(-2.5f));
        }
    }

    @EventHandler
    public void ph(ProjectileHitEvent event){
        if(event.getEntity().hasMetadata("Getsuga")){
            event.setCancelled(true);
            event.getEntity().remove();
            if(event.getHitEntity() instanceof LivingEntity){
                LivingEntity e = (LivingEntity) event.getHitEntity();
                e.damage(e.getHealth());
            }
        }
    }

    @EventHandler
    public void transf(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(Utils.color("&0Char: Ichigo"))){
            if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
                plugin.getConfig().set(player.getUniqueId().toString() + ".transform", 0);
                plugin.saveConfig();
                player.getInventory().setItem(0, BleachItems.ichigoZangetsu());
                player.getInventory().setItem(1, BleachItems.ichigoTransforms());
            }
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                int transform = plugin.getConfig().getInt(player.getUniqueId().toString() + ".transform");
                switch(transform){
                    case 0:
                        //Default
                        freedom.put(player, false);
                        player.setInvisible(true);
                        //Create NPC
                        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
                        Vector dir = player.getLocation().getDirection();
                        dir.multiply(7);

                        Location loc = player.getLocation().add(dir);
                        loc.setY(player.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ())+ 1.5f);
                        loc.setYaw(player.getLocation().getYaw() + 180);
                        npc.spawn(loc);
                        npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, player.getInventory().getItem(0));
                        player.sendTitle(Utils.color("&e&kE&cWAKE UP ZANGETSU&e&kE"), Utils.color("&eFight with me"), 20, 40, 20);
                        new BukkitRunnable(){
                            int count = 0;
                            Entity npc1 = npc.getEntity();
                            @Override
                            public void run() {
                                player.getInventory().setHeldItemSlot(0);
                                for(int i = 0; i<10;i++){
                                    player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 2, 5,0.15, 5, new Particle.DustOptions(Color.fromRGB(244, 54, 31),3));
                                    player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 2, 5,0.15, 5, new Particle.DustOptions(Color.fromRGB(244, 170, 31),3));
                                    player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 2, 5,0.15, 5, new Particle.DustOptions(Color.fromRGB(244, 231, 31),3));

                                }

                                if(count % 10 == 0){
                                    for(int i =0; i < 360; i++){
                                        player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX() + Math.sin(Math.toRadians(i)), npc1.getLocation().getY() + (i / 180f), npc1.getLocation().getZ() +  Math.cos(Math.toRadians(i)), 1, 0,0, 0, new Particle.DustOptions(Color.fromRGB(78, 223, 243),1));
                                        player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX() + (-1 *Math.sin(Math.toRadians(i))), npc1.getLocation().getY() + (i / 180f), npc1.getLocation().getZ() + (-1 * Math.cos(Math.toRadians(i))), 1, 0,0, 0, new Particle.DustOptions(Color.fromRGB(31, 47, 244),1));
                                    }
                                }

                                count++;
                                if(count > 70){
                                    player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 5, 1,0.15, 1);
                                }
                                if(count == 100){
                                    freedom.put(player, true);
                                    npc.destroy();
                                    player.setInvisible(false);
                                    ItemMeta meta = player.getInventory().getItem(0).getItemMeta();
                                    meta.setDisplayName(Utils.color("&bZangetsu (Shikai)"));
                                    player.getInventory().getItem(0).setItemMeta(meta);
                                    ItemMeta meta1 = player.getInventory().getItem(1).getItemMeta();
                                    meta1.setDisplayName(Utils.color("&bTransformations (Shikai)"));
                                    player.getInventory().getItem(1).setItemMeta(meta1);
                                    plugin.getConfig().set(player.getUniqueId().toString() + ".transform", 1);
                                    plugin.saveConfig();
                                    cancel();
                                }

                            }
                        }.runTaskTimer(plugin, 1,1);

                        break;
                    case 1:
                        //Shikai
                        freedom.put(player, false);
                        player.setInvisible(true);
                        //Create NPC
                        NPC npc2 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
                        Vector dir1 = player.getLocation().getDirection();
                        dir1.multiply(7);

                        Location loc1 = player.getLocation().add(dir1);
                        loc1.setY(player.getWorld().getHighestBlockYAt((int) loc1.getX(), (int) loc1.getZ())+ 1.5f);
                        loc1.setYaw(player.getLocation().getYaw() + 180);
                        npc2.spawn(loc1);
                        npc2.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, player.getInventory().getItem(0));
                        List<String> titles = new ArrayList<>();
                        titles.add(Utils.color("&e&kE&cBAN&e&kE"));
                        titles.add(Utils.color("&e&kE&cKAI&e&kE"));
                        titles.add(Utils.color("&e&kE&cTENSA ZANGETSU&e&kE"));
                        Utils.multipleTitles(player,plugin,titles,10,20,10);
                        new BukkitRunnable(){
                            int count = 0;
                            Entity npc1 = npc2.getEntity();
                            @Override
                            public void run() {
                                player.getInventory().setHeldItemSlot(0);
                                for(int i = 0; i<30;i++){
                                    player.getWorld().spawnParticle(Particle.FLAME, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 2, 1,0.15, 1);

                                }

                                if(count % 10 == 0){
                                    for(int i =0; i < 360; i++){
                                        player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX() + Math.sin(Math.toRadians(i)), npc1.getLocation().getY() + (i / 180f), npc1.getLocation().getZ() +  Math.cos(Math.toRadians(i)), 1, 0,0, 0, new Particle.DustOptions(Color.fromRGB(0,0,0),1));
                                        player.getWorld().spawnParticle(Particle.REDSTONE, npc1.getLocation().getX() + (-1 *Math.sin(Math.toRadians(i))), npc1.getLocation().getY() + (i / 180f), npc1.getLocation().getZ() + (-1 * Math.cos(Math.toRadians(i))), 1, 0,0, 0, new Particle.DustOptions(Color.fromRGB(255,255,255),1));
                                    }
                                }

                                count++;
                                if(count > 110){
                                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, npc1.getLocation().getX(), npc1.getLocation().getY(), npc1.getLocation().getZ(), 10, 1.5,0.15, 1.5);
                                }
                                if(count == 140){
                                    freedom.put(player, true);
                                    npc2.destroy();
                                    player.setInvisible(false);
                                    ItemMeta meta = player.getInventory().getItem(0).getItemMeta();
                                    meta.setDisplayName(Utils.color("&7&kEE&c&lTensa Zangetsu (Bankai)&7&kEE"));
                                    player.getInventory().getItem(0).setType(Material.NETHERITE_SWORD);
                                    player.getInventory().getItem(0).setItemMeta(meta);
                                    ItemMeta meta1 = player.getInventory().getItem(1).getItemMeta();
                                    meta1.setDisplayName(Utils.color("&bTransformations (Bankai)"));
                                    player.getInventory().getItem(1).setItemMeta(meta1);
                                    plugin.getConfig().set(player.getUniqueId().toString() + ".transform", 2);
                                    plugin.saveConfig();
                                    addIchigoPotions(player);
                                    cancel();
                                }

                            }
                        }.runTaskTimer(plugin, 1,1);
                        break;
                }
            }
        }
    }

    public void addIchigoPotions(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100*20*60, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100*20*60, 3, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100*20*60, 1, false, false));
        new BukkitRunnable(){

            @Override
            public void run() {
                if(plugin.getConfig().getInt(player.getUniqueId().toString() + ".transform") < 2){
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    player.removePotionEffect(PotionEffectType.SPEED);
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 20,20);
    }
}
