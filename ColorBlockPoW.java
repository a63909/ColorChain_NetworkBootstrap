package com.example.tamarashadow.core;
import com.example.tamarashadow.core.ColorBlockPoW;
import com.example.tamarashadow.core.ColorBlock;


public class ColorBlockPoW extends ColorBlock {
    private String prevHash;
    private String hash;
    private String colorCode;
    private int x;
    private int y;
    private long timestamp;
    private int nonce;
    private String minerId;
    private String blockHash;

    public ColorBlockPoW(String prevHash, String colorCode, int x, int y, long timestamp, String minerId) {
        super(prevHash, colorCode, minerId);

        this.prevHash = prevHash;
        this.colorCode = colorCode;
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.minerId = minerId;
        this.nonce = 0;
    }

    public void setBlockHash(String hash) {
        this.blockHash = hash;
    }

    public void mine() {
        int difficulty = 4;
        String targetPrefix = "0".repeat(difficulty);
        nonce = 0;

        while (true) {
            String attempt = calculateHash();
            if (attempt.startsWith(targetPrefix)) {
                this.hash = attempt;
                break;
            }
            nonce++;
        }

        System.out.println("[✅] Блок замайнен с nonce = " + nonce + ", hash = " + hash);
    }

    private String calculateHash() {
        String data = prevHash + colorCode + x + y + timestamp + minerId + nonce;
        return HashUtil.sha256(data);
    }

    public String getHash() {
        return hash;
    }

    public int getNonce() {
        return nonce;
    }

    public String getColorCode() {
        return colorCode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMinerId() {
        return minerId;
    }

    @Override
    public String toString() {
        return "ColorBlockPoW{" +
                "hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", minerId='" + minerId + '\'' +
                '}';
    }
}
