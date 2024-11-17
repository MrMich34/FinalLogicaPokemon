package com.org;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.Pelea;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {

    static String botToken = "8049902067:AAGdvNqXVlFf5U-EpsrDNi7aSLGgetsL92A";
    static TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

    public static void main(String[] args) throws IOException, TelegramApiException {

        String broker = "tcp://localhost:1883"; // Dirección del broker
        String clientId = "JavaReceiver"; // ID único para el cliente suscriptor
        String topic = "pokemon/pelea";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Conexión perdida: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("Mensaje recibido del tema " + topic + ": " + payload);
                    procesarMensaje(payload); // Procesar el JSON recibido
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // No se utiliza en un cliente suscriptor
                }
            });

            client.connect();
            client.subscribe(topic);
            System.out.println("Suscrito al tema " + topic);




            // Mantener el cliente en ejecución
            synchronized (Main.class) {
                Main.class.wait();
            }



        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void procesarMensaje(String jsonString) {
        try {
            String ganador;
            ObjectMapper objectMapper = new ObjectMapper();
            Pelea pelea = objectMapper.readValue(jsonString, Pelea.class); // Deserializa el JSON a un objeto Pelea

            // Imprime detalles de la pelea para verificar la deserialización
            System.out.println("Pelea recibida:");
            System.out.println("Pokemon 1: " + pelea.getPokemon1().getNombre());
            System.out.println("Pokemon 2: " + pelea.getPokemon2().getNombre());
            ganador = pelea.iniciarPelea();
            enviar(pelea.getPokemon1().getNombre(),pelea.getPokemon2().getNombre(),pelea, ganador);
            // Agrega más detalles si es necesario
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enviar(String poke1, String poke2, Pelea pelea, String ganador) throws IOException {

        String broker = "tcp://localhost:1883";  // Dirección del servidor Mosquitto
        String clientId = "JavaPublisher";      // ID único para el cliente
        String topic = "pokemon/imagenes";            // Tema al que publicar
        String content = "Hola desde Java!";    // Contenido del mensaje

        try {
            // Crear el cliente MQTT
            MqttClient client = new MqttClient(broker, clientId);

            // Configurar opciones de conexión
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            // Conectar al broker MQTT
            System.out.println("Conectando al broker...");
            client.connect(options);
            System.out.println("Conectado al broker.");

            // Crear el mensaje

            String s = pelea.getPokemon1().getImagen();
            String del = " @@@ ";
            String s2 = pelea.getPokemon2().getImagen();
            String t = poke1 +del+poke2+del+s+del+s2+del+ganador;

            MqttMessage message = new MqttMessage(t.getBytes());
            message.setQos(2);  // Calidad de servicio (QoS): 0, 1 o 2

            // Publicar el mensaje en el tema especificado
            System.out.println("Publicando el mensaje...");
            System.out.println(t);
            client.publish(topic, message);
            System.out.println("Mensaje publicado.");

            // Desconectar después de publicar
            client.disconnect();

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}