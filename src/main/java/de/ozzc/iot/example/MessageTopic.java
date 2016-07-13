package de.ozzc.iot.example;

import de.ozzc.iot.util.IoTConfig;
import de.ozzc.iot.util.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;

import java.awt.*;
import java.util.Random;

import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_CLIENT_ID;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_HOST;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_PORT;

/**
 * Created by Tatiana on 08.07.2016.
 */
public class MessageTopic {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTopic.class);

    private static final int QOS_LEVEL = 0;
    private static final String TOPIC = "MyTopic";
    private static final String MESSAGE = "Hello World!";
    private static final long QUIESCE_TIMEOUT = 5000;
    private static final String TOPIC_LOG = "Topic_Log";
    TextArea textArea;

    public MessageTopic(TextArea textArea){
        this.textArea = textArea;
    }

    public void sendMessage(String message){
        try{
        IoTConfig config = new IoTConfig("C:\\javaAws\\config.properties");//args[0]);
        SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
                "C:\\javaAws\\root.cer",
                "C:\\javaAws\\6a41a41d17-certificate_pem.cer",
                "C:\\javaAws\\6a41a41d17-private_pem.key");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(sslSocketFactory);
        options.setCleanSession(true);

        final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) + ":" + config.get(AWS_IOT_MQTT_PORT);
        final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);



        MqttClient client = new MqttClient(serverUrl, clientId);

        client.setCallback(new ExampleCallback(textArea));
        client.connect(options);
        client.subscribe(TOPIC_LOG, QOS_LEVEL);
        client.publish(TOPIC, new MqttMessage(message.getBytes()));


        // Remove the disconnect and close, if you want to continue listening/subscribing
//        client.disconnect(QUIESCE_TIMEOUT);
//            client.close();
    } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        System.exit(-1);
    }


}

    private static void showHelp() {
        System.out.println("Usage: java -jar aws-iot-java-example.jar <config-file>");
        System.out.println("\nSee config-example.properties for an example of a config file.");
        System.exit(0);
    }


}
