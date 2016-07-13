package de.ozzc.iot.example;

import de.ozzc.iot.gui.MyRobotForm;
import de.ozzc.iot.util.IoTConfig;
import de.ozzc.iot.util.SslUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.ozzc.iot.util.IoTConfig.ConfigFields.*;

/**
 * Simple MQTT Client Example for Publish/Subscribe on AWS IoT.
 * This example should serve as a starting point for using AWS IoT with Java.
 *
 * <ul>
 *  <li>The client connects to the endpoint specified in the config file.</li>
 *  <li>Subscribes to the topic "MyTopic".</li>
 *  <li>Publishes  a "Hello World" message to the topic "MyTopic.</li>
 *  <li>Closes the connection.</li>
 *  <li>This example should serve as a starting point for using AWS IoT with Java.</li>
 * </ul>
 * Created by Ozkan Can on 04/09/2016.
 */
public class Main extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int QOS_LEVEL = 0;
    private static final String TOPIC = "MyTopic";
    private static final String MESSAGE = "Hello World!";
    private static final long QUIESCE_TIMEOUT = 5000;


        public static void main(String[] args) {

            JFrame frame = new JFrame("ColorPicker");
            frame.setSize(300, 400);
            frame.setResizable(false);
            frame.setContentPane(new MyRobotForm().getPanelMain());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            if (args.length < 1) {
                showHelp();
            }

//            try {
//                IoTConfig config = new IoTConfig("C:\\javaAws\\config.properties");//args[0]);
//                SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
//                        "C:\\javaAws\\root.cer",
//                        "C:\\javaAws\\6a41a41d17-certificate_pem.cer",
//                        "C:\\javaAws\\6a41a41d17-private_pem.key");
//                MqttConnectOptions options = new MqttConnectOptions();
//                options.setSocketFactory(sslSocketFactory);
//                options.setCleanSession(true);
//
//                final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) + ":" + config.get(AWS_IOT_MQTT_PORT);
//                final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);
//
//                MqttClient client = new MqttClient(serverUrl, clientId);
//                client.setCallback(new ExampleCallback());
//                client.connect(options);
//                client.subscribe(TOPIC, QOS_LEVEL);
//                client.publish(TOPIC, new MqttMessage(MESSAGE.getBytes()));

                // Remove the disconnect and close, if you want to continue listening/subscribing
//            client.disconnect(QUIESCE_TIMEOUT);
//            client.close();
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//                System.exit(-1);
//            }


        }

        private static void showHelp() {
            System.out.println("Usage: java -jar aws-iot-java-example.jar <config-file>");
            System.out.println("\nSee config-example.properties for an example of a config file.");
            System.exit(0);
        }
    }

