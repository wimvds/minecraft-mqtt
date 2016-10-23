package be.bonjourmicro.mqtt;

import java.util.StringJoiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author wim
 */
public class Mqtt extends JavaPlugin {

    MinecraftMqttClient client;
    
    final String BROKER_URL = "tcp://192.168.1.11:1883";
    final String TOPIC = "demo/minecraft";
    final String CLIENT_ID = "MinecraftMQTT";

    @Override
    public void onEnable() {
        // Bukkit.getServer().getPluginManager().registerEvents(this, this);
        client = new MinecraftMqttClient(Bukkit.getServer().getLogger());
        try {
            client.setup(BROKER_URL, CLIENT_ID, TOPIC);
        } catch (MqttException e) {
            Bukkit.getServer().getLogger().severe("Failed setting up connnection with MQTT broker!");
        }
    }

    @Override
    public void onDisable() {
        try {
            client.disconnect();            
        } catch (MqttException e) {
            Bukkit.getServer().getLogger().severe("Failed disconnecting from MQTT broker!");
        }
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

        try {
            StringJoiner joiner = new StringJoiner(" ");
            for (int i=1; i<args.length; i++) {
                joiner.add(args[i]);                
            }
            sender.sendMessage(ChatColor.GREEN + "Sending payload " + joiner.toString() + " to topic " + args[0]);
            client.send(args[0], joiner.toString());
        } catch (MqttException e) {
            Bukkit.getServer().getLogger().severe("Failed sending message to MQTT broker!");
        }

        return true;
    }
    
}
