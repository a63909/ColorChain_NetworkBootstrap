package com.example.tamarashadow.core;

import java.net.InetSocketAddress;
import java.net.URI;

public class NodeLauncher {

    public static void main(String[] args) {
        int port = 1001; // Порт нашей ноды

        // Запускаем сервер
        PeerServer server = new PeerServer(new InetSocketAddress(1001));
        server.start();

        server.start();
        System.out.println("[🌐] Нода запущена на порту " + port);

        // Подключаемся к другому узлу (если указан адрес)
        if (args.length > 0) {
            try {
                String targetUri = args[0];
                PeerClient client = new PeerClient(new URI(targetUri));
                client.connect();
                System.out.println("[📡] Подключение к другому узлу: " + targetUri);
            } catch (Exception e) {
                System.err.println("[⚠️] Ошибка подключения к другому узлу: " + e.getMessage());
            }
        } else {
            System.out.println("[ℹ️] Другие узлы не указаны, только приём соединений.");
        }
    }
}
