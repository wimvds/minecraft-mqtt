/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.bonjourmicro.mqtt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author wim
 */
public class Mqtt extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getLogger().info("Mqtt plugin enabled! - We should probably connect to MQTT broker here");
        
        // Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("Mqtt plugin disabled! - We should probably disconnect from MQTT broker here");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (label.equalsIgnoreCase("sendmqtt")) {
            return this.sendMqtt(sender, args);
        }
        
        return true;
    }

    private boolean sendMqtt(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "The sendmqtt command needs a topic and a payload as parameters!");

            return false;
        }
        // TODO : Actually send payload via MQTT
        sender.sendMessage(ChatColor.GREEN + "Sending payload " + args[1] + " to topic " + args[0]);
        
        return true;
    }
    
}
