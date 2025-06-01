package com.example.tamarashadow.core;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ColorNode {
    private int port;
    private static List<String> peers = new ArrayList<>();
    private static ColorNode instance;

    private PeerServer server;

    // Синглтон-доступ
    public static ColorNode getInstance() {
        if (instance == null) {
            instance = new ColorNode(1001, new ArrayList<>());
        }
        return instance;
    }

    // Конструктор
    public ColorNode(int port, List<String> peers) {
        this.port = port;
        ColorNode.peers = peers;
        instance = this;

        discoverPeers(); // Автообнаружение

        ColorNodeManager.getInstance().setCurrentNode(this);
    }

    public static void printPeers() {
        System.out.println("[🌐] Подключенные узлы:");
        for (String peer : peers) {
            System.out.println(" - " + peer);
        }
    }

    public void connectToPeer(String ip, int port) {
        try {
            String target = ip + ":" + port;
            PeerClient client = new PeerClient(new URI("ws://" + target));
            client.connect();
            peers.add(target);
            System.out.println("[🔌] Подключились к узлу: " + target);
        } catch (Exception e) {
            System.out.println("[❌] Не удалось подключиться к узлу: " + ip + ":" + port);
        }
    }

    private void discoverPeers() {
        String localIp = NetworkUtils.getLocalIpAddress();
        if (localIp == null) {
            System.out.println("[⚠️] Невозможно определить локальный IP.");
            return;
        }

        String base = localIp.substring(0, localIp.lastIndexOf(".") + 1);
        for (int i = 1; i <= 254; i++) {
            String target = base + i + ":1001";
            if (target.equals(localIp + ":1001")) continue;

            try {
                PeerClient client = new PeerClient(new URI("ws://" + target));
                client.connect();
                peers.add(target);
                System.out.println("[🔍] Попытка подключения к: " + target);
            } catch (Exception ignored) {
                // Игнорируем ошибки подключения
            }
        }
    }

    public void broadcast(String message) {
        for (WebSocket client : PeerClient.getAllClients()) {
            try {
                client.send(message);
            } catch (Exception e) {
                System.out.println("[💥] Ошибка при рассылке: " + e.getMessage());
            }
        }
    }

    public List<String> getPeers() {
        return peers;
    }
}
