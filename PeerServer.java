package com.example.tamarashadow.core;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PeerServer extends WebSocketServer {

    private static final Set<WebSocket> activePeers = Collections.synchronizedSet(new HashSet<>());

    public PeerServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        activePeers.add(conn);
        System.out.println("[🌐] Принято новое подключение: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        activePeers.remove(conn);
        System.out.println("[❌] Отключение клиента: " + conn.getRemoteSocketAddress() + ", причина: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("[📥] Получено сообщение: " + message);
        // Здесь можно добавить обработку входящих блоков и транзакций
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("[💥] Ошибка: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("[✅] PeerServer запущен и слушает подключения.");
    }

    public static Set<WebSocket> getActivePeers() {
        return activePeers;
    }
}
