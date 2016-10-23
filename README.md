# Mqtt plugin

A basic MQTT plugin for Spigot/Bukkit that will allow you to send MQTT messages 
from within Minecraft.

To use this plugin, compile it to a .jar file and put it in your Spigot/Bukkit 
plugins folder. Once there it should automatically load when you (re)start your 
Minecraft server.

This plugin depends on org.eclipse.paho.client.mqttv3-1.1.0.jar, you will have to 
put that in a lib folder below the Spigot/Bukkit plugins folder.

TODO :
- Set MQTT broker config in a config file
- Make topic listener optional
- Make topic listener execute Minecraft commands by default
