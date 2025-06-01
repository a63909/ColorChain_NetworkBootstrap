package com.example.tamarashadow.core;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PeerClient extends WebSocketClient {

    private static final List<WebSocket> clients = new ArrayList<>();


    public PeerClient(URI serverUri) {
        super(serverUri);
    }

    public static List<WebSocket> getAllClients() {
        return new ArrayList<>(clients); // clients должен быть статическим полем в PeerClient
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        clients.add(this); // если this является WebSocket
    }


    @Override
    public void onMessage(String message) {
        System.out.println("[📨] Получено сообщение от узла: " + message);
        // Здесь будет логика обработки блока / транзакции
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[❌] Соединение с узлом закрыто: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("[💥] Ошибка клиента: " + ex.getMessage());
    }

    public void sendMessage(String msg) {
        send(msg);
        System.out.println("[📤] Отправлено сообщение: " + msg);
    }
}
