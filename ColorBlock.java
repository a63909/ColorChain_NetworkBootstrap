// Обновлённый ColorBlock с поддержкой ColorDTO
package com.example.tamarashadow.core;
import com.example.tamarashadow.core.ColorBlockPoW;
import com.example.tamarashadow.core.ColorBlock;



import java.util.*;

public class ColorBlock {
    private String previousHash;
    private String blockHash;
    private String color;
    private String minerId;
    private String signature;
    private long timestamp;
    private List<ColorTransaction> transactions = new ArrayList<>();
    private List<ColorDTO> dtoList; // новое поле для поддержки ColorDTO
    private ColorToken token;
    public void setToken(ColorToken token) {
        this.token = token;
    }

    public ColorToken getToken() {
        return token;
    }


    public ColorBlock(String previousHash, String color, String minerId) {
        this.previousHash = previousHash;
        this.color = color;
        this.minerId = minerId;
        this.timestamp = System.currentTimeMillis();
        generateHash();
        addCoinbaseTransaction();
    }

    public List<ColorDTO> getColorDNA() {
        return dtoList;
    }

    public ColorBlock(String previousHash, List<ColorDTO> dtoList, String minerId) {
        this.previousHash = previousHash;
        this.dtoList = dtoList;
        this.color = extractDominantColor(dtoList); // получаем основной цвет
        this.minerId = minerId;
        this.timestamp = System.currentTimeMillis();
        generateHash();
        addCoinbaseTransaction();
    }

    private String extractDominantColor(List<ColorDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) return "#FFFFFF";
        ColorDTO color = dtoList.get(0);
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

    }

    private void addCoinbaseTransaction() {
        ColorTransaction coinbase = new ColorTransaction("COINBASE", minerId, 1, "");
        transactions.add(coinbase);
    }

    public void addTransaction(ColorTransaction tx) {
        transactions.add(tx);
    }

    public List<ColorTransaction> getTransactions() {
        return transactions;
    }

    public void generateHash() {
        this.blockHash = Integer.toHexString((previousHash + color + minerId + timestamp).hashCode());
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getColor() {
        return color;
    }

    public String getMinerId() {
        return minerId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<ColorDTO> getDtoList() {
        return dtoList;
    }

    @Override
    public String toString() {
        return "[Блок] Hash: " + blockHash + " | Цвет: " + color + " | Майнер: " + minerId +
                " | Транзакций: " + transactions.size();
    }
}
