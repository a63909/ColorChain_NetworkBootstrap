// Шаг 2: ColorTransaction.java — транзакция в ColorChain
package com.example.tamarashadow.core;


public class ColorTransaction {
    private String from;       // publicKey или "COINBASE"
    private String to;         // publicKey получателя
    private int amount;        // Количество lba₽
    private String signature;  // подпись отправителя (если не coinbase)

    public ColorTransaction(String from, String to, int amount, String signature) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signature;
    }

    public boolean isCoinbase() {
        return "COINBASE".equals(from);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public String getSignature() {
        return signature;
    }

    public String getRawData() {
        return from + to + amount;
    }

    @Override
    public String toString() {
        return "TX [" + from + " → " + to + ": " + amount + " lba₽]";
    }
}
