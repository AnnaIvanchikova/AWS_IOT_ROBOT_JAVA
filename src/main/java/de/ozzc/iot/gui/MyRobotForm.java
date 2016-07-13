package de.ozzc.iot.gui;

import de.ozzc.iot.example.ExampleCallback;
import de.ozzc.iot.example.MessageTopic;
import de.ozzc.iot.util.IoTConfig;
import de.ozzc.iot.util.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_CLIENT_ID;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_HOST;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_PORT;

/**
 * Created by Tatiana on 07.07.2016.
 */
public class MyRobotForm extends JFrame{
    private JButton buttonLeft = new JButton("left");
    private JButton buttonRight = new JButton("right");
    private JButton buttonGo = new JButton("go");
    private JButton buttonStop = new JButton("stop");
    private JPanel panelMain;
    private JTextField textField;
    public TextArea textArea;

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRobotForm.class);

    private static final int QOS_LEVEL = 0;
    private static final String TOPIC = "MyTopic";
    private static final String MESSAGE = "Hello World!";
    private static final long QUIESCE_TIMEOUT = 5000;
    private static final String TOPIC_LOG = "Topic_Log";

    MqttClient client;



    public MyRobotForm(){
        super("robot");
        createGUI();


    }

    public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelMain = new JPanel();
        panelMain.setLayout(new FlowLayout());
        buttonLeft.setActionCommand("left");
        panelMain.add(buttonLeft);
        buttonRight.setActionCommand("right");
        panelMain.add(buttonRight);
        buttonGo.setActionCommand("go");
        panelMain.add(buttonGo);
        buttonStop.setActionCommand("stop");
        panelMain.add(buttonStop);
        textArea = new TextArea(10, 20);

       // final MessageTopic messageTopic = new MessageTopic(textArea);
        panelMain.add(new JScrollPane(textArea), BorderLayout.CENTER);
        initClientMessage(textArea);
        buttonGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "go";
                textArea.append(source + "\n");
                try {
                    client.publish(TOPIC, new MqttMessage(source.getBytes()));
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "stop";
                textArea.append(source + "\n");
                try {
                    client.publish(TOPIC, new MqttMessage(source.getBytes()));
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "right";
                textArea.append(source + "\n");
                try {
                    client.publish(TOPIC, new MqttMessage(source.getBytes()));
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = "left";
                textArea.append(source + "\n");
                try {
                    client.publish(TOPIC, new MqttMessage(source.getBytes()));
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
            }
        });



    }

    public void initClientMessage(TextArea textAreas){
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

            client = new MqttClient(serverUrl, clientId);

            client.setCallback(new ExampleCallback(textAreas));
            client.connect(options);
            client.subscribe(TOPIC_LOG, QOS_LEVEL);



            // Remove the disconnect and close, if you want to continue listening/subscribing
//        client.disconnect(QUIESCE_TIMEOUT);
//            client.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(-1);
        }


    }

//    public class TestActionListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            textField.setText(e.getActionCommand());
//        }
//    }


    public JButton getButtonLeft() {
        return buttonLeft;
    }

    public void setButtonLeft(JButton buttonLeft) {
        this.buttonLeft = buttonLeft;
    }

    public JButton getButtonRight() {
        return buttonRight;
    }

    public void setButtonRight(JButton buttonRight) {
        this.buttonRight = buttonRight;
    }

    public JButton getButtonGo() {
        return buttonGo;
    }

    public void setButtonGo(JButton buttonGo) {
        this.buttonGo = buttonGo;
    }

    public JButton getButtonStop() {
        return buttonStop;
    }

    public void setButtonStop(JButton buttonStop) {
        this.buttonStop = buttonStop;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
}
