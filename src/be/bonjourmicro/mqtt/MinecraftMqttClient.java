package be.bonjourmicro.mqtt;

import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author wim
 */
public class MinecraftMqttClient implements MqttCallback {
    
    MqttClient client;
    MqttConnectOptions connOpt;
    Logger logger;

    public MinecraftMqttClient(Logger logger) {
        this.logger = logger;
    }
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        logger.severe("MQTT - Connection lost."); 
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {        
        logger.info("Message arrived for topic " + topic + ": " + mm.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        logger.info("Delivery complete!");
    }

    public void setup(String brokerUrl, String clientId, String topic) throws MqttException {
        // Start connection
        connOpt = new MqttConnectOptions();
	connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        client = new MqttClient(brokerUrl, clientId);
        client.setCallback(this);
        client.connect(connOpt);

        // Setup subscriber
        int subQoS = 0;
        client.subscribe(topic, subQoS);
    }
    
    public void send(String topic, String payload) throws MqttException {
        int pubQoS = 0;
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        if (client.isConnected()) {
            client.publish(topic, message);
        }
    }
    
    public void disconnect() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();            
        }
    }
}
