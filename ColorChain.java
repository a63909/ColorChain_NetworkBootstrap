package com.example.tamarashadow.core;
import static com.example.tamarashadow.core.ColorTouchMiner.*;
import static com.example.tamarashadow.core.ColorTouchMiner.getBlockchain;
import static com.example.tamarashadow.core.ColorTouchMiner.getWallet;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.example.tamarashadow.core.ColorInventory;


public class ColorChain {
    private static List<ColorBlock> blockchain = new ArrayList<>();


    // 🔒 Статический список токенов — общее хранилище
    private static List<ColorToken> tokens = new ArrayList<>();

    public static List<ColorBlock> getChain() {
        return blockchain;
    }


    private static int lbaPCount = 0;

    // 🔗 Список блоков в цепочке
    private final List<ColorBlock> chain;

    public ColorChain() {
        this.chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    public static boolean containsHash(String hash) {
        for (ColorBlock block : getBlockchain()) {
            if (block.getBlockHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }


    private static List<ColorBlockPoW> powChain = new ArrayList<>();

    public static void addBlock(ColorBlockPoW block) {
        powChain.add(block);
        System.out.println("🧱 Блок добавлен в цепь. Всего блоков: " + powChain.size());
    }


    // ✅ Под Android — загрузка токенов через контекст
    public static void loadTokens(Context context) {
        ColorInventory.loadTokens(context);
    }

    // 🧬 Генерация первого блока
    private ColorBlock createGenesisBlock() {
        List<ColorDTO> genesisColors = new ArrayList<>();
        genesisColors.add(new ColorDTO(255, 255, 255, 0, 0));

        try {
            ColorWallet wallet = ColorWallet.loadOrCreate();
            return new ColorBlock("0", genesisColors, wallet.getPublicKeyString());
        } catch (Exception e) {
            throw new RuntimeException("❌ Не удалось создать кошелёк", e);
        }
    }


    public static void createGenesisToken(String ownerId) {
        ColorToken origin = ColorToken.createOriginToken(ownerId);
        tokens.add(origin);
        System.out.println("[GENESIS] 🌱 OriginToken создан: " + origin);
    }




    public static int getBalanceForCurrentMiner() {
        // ВРЕМЕННЫЙ идентификатор майнера
        String minerId = "default-miner"; // потом заменим на реальный ID
        return getBalanceForMiner(minerId);
    }

    public static int getBalanceForMiner(String minerId) {
        int total = 0;
        for (ColorToken token : tokens) {
            if (token.getOwnerId().equals(minerId)) {
                total += token.getStrength();// или token.getValue()
            }
        }
        return total;
    }



    // ➕ Добавление стандартного блока
    public void addBlock(List<ColorDTO> colorDNA) {
        try {
            ColorWallet wallet = ColorWallet.loadOrCreate();
            ColorBlock lastBlock = getLastBlock();
            ColorBlock newBlock = new ColorBlock(lastBlock.getBlockHash(), colorDNA, wallet.getPublicKeyString());
            chain.add(newBlock);
        } catch (Exception e) {
            Log.e("ColorChain", "❌ Ошибка создания блока: " + e.getMessage());
        }
    }

    // ➕ Продвинутая вставка
    public void addBlock(List<ColorDTO> colorDNA, int x, int y, String phrase, long timestamp) {
        try {
            ColorWallet wallet = ColorWallet.loadOrCreate();
            ColorBlock lastBlock = getLastBlock();
            ColorBlock newBlock = new ColorBlock(lastBlock.getBlockHash(), colorDNA, wallet.getPublicKeyString());
            chain.add(newBlock);
        } catch (Exception e) {
            Log.e("ColorChain", "❌ Ошибка расширенного блока: " + e.getMessage());
        }
    }

    // 📥 Добавить токен
    public static void addToken(ColorToken token) {
        tokens.add(token);

        if (token.isLbaP()) {
            lbaPCount++;
        }
    }

    public static int getLbaPCount() {
        return lbaPCount;
    }

    // 📤 Получить токены
    public static List<ColorToken> getTokens() {
        return tokens;
    }

    // ♻️ Заменить весь список токенов
    public static void setTokens(List<ColorToken> newTokens) {
        tokens = newTokens;
    }

    // 🧱 Получить последний блок
    public ColorBlock getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    // 📦 Получить все блоки
    public List<ColorBlock> getBlocks() {
        return chain;
    }

    // 🖨️ Вывод всей цепочки (в лог)
    public void printChain() {
        for (ColorBlock block : chain) {
            Log.d("ColorChain", block.toString());
        }

        Log.d("ColorChain", "=== Список токенов ===");
        for (ColorToken token : tokens) {
            Log.d("ColorChain", token.toString());
        }
    }
}
