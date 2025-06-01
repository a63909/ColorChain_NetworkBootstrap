package com.example.tamarashadow.core;

import java.util.*;
import com.example.tamarashadow.network.NodeManager;
import com.example.tamarashadow.network.NodeClient;

public class ColorTouchMiner {
    private static final List<ColorBlock> blockchain = new ArrayList<>();
    private static final Set<String> minedColors = new HashSet<>();
    private static final ColorWallet wallet = new ColorWallet();
    private static String prevHash = "0000000000000000";
    private static ColorToken currentToken = ColorToken.createOriginToken("system");

    public static int touch(ColorDTO color) {
        String colorCode = color.toSimpleString();

        if (isColorAlreadyMined(color)) {
            return 0;
        }

        System.out.println("[🎨] Касание цвета: " + colorCode);
        System.out.println("[⚙] Запуск майнинга блока...");

        ColorBlockPoW block = new ColorBlockPoW(prevHash, colorCode, color.getX(), color.getY(), System.currentTimeMillis(), "user");
        block.mine();
        blockchain.add(block);

        String json = "{"
                + "\"type\":\"newBlock\","
                + "\"hash\":\"" + block.getBlockHash() + "\","
                + "\"color\":\"" + block.getColorCode() + "\","
                + "\"miner\":\"" + block.getMinerId() + "\","
                + "\"timestamp\":" + block.getTimestamp()
                + "}";

        // ✅ Отправка в отдельном потоке для каждого peer
        for (String peer : NodeManager.getKnownPeers()) {
            new Thread(() -> {
                try {
                    NodeClient.connectAndSend(peer, json);
                } catch (Exception e) {
                    System.out.println("[ТАМАРА] ⚠️ Не удалось отправить блок узлу " + peer + ": " + e.getMessage());
                }
            }).start();
        }

        prevHash = block.getHash();

        // Увеличиваем силу текущего токена
        currentToken.incrementStrength();

        // Эволюция токена
        currentToken.evolve();
        if (currentToken.isLbaP()) {
            System.out.println("💎 Токен стал LBU₽");
        }

        // Добавление токена в кошелёк (один раз)
        if (!wallet.getTokens().contains(currentToken)) {
            wallet.addToken(currentToken);
        }

        minedColors.add(colorCode);
        return 1;
    }

    public static boolean isColorAlreadyMined(ColorDTO color) {
        return minedColors.contains(color.toSimpleString());
    }

    public static void setOriginToken(ColorToken token) {
        currentToken = token;
        wallet.addToken(currentToken);
    }

    public static List<ColorBlock> getBlockchain() {
        return blockchain;
    }

    public static ColorWallet getWallet() {
        return wallet;
    }
}
