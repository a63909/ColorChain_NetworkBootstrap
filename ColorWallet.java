// ColorWallet.java (исправленный)
package com.example.tamarashadow.core;

import android.content.Context;

import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ColorWallet {
    private static File getWalletFile() {
        return new File(appContext.getFilesDir(), "wallet.json");
    }

    private static Context appContext;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String ownerId = "ORIGIN"; // временный фикс
    private List<ColorToken> tokens = new ArrayList<>();

    public ColorWallet() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            this.publicKey = pair.getPublic();
            this.privateKey = pair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void add(ColorToken token) {
        tokens.add(token);
    }

    public void addToken(ColorToken token) {
        tokens.add(token);
    }

    public int getBalance() {
        return tokens.size();
    }

    public int getLbaPBalance() {
        return (int) tokens.stream().filter(ColorToken::isLbaP).count();
    }

    public int getTotalValue() {
        return tokens.stream().mapToInt(ColorToken::getStrength).sum();
    }

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }


    public void clear() {
        tokens.clear();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<ColorToken> getTokens() {
        return tokens;
    }

    public void saveToFile() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getWalletFile()))) {
            writer.write(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            writer.newLine();
            writer.write(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            writer.newLine();
        }
    }


    public static ColorWallet loadOrCreate() throws Exception {
        File file = getWalletFile();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String pub = reader.readLine();
                String priv = reader.readLine();

                KeyFactory kf = KeyFactory.getInstance("RSA");
                byte[] pubBytes = Base64.getDecoder().decode(pub);
                byte[] privBytes = Base64.getDecoder().decode(priv);

                PublicKey publicKey = kf.generatePublic(new java.security.spec.X509EncodedKeySpec(pubBytes));
                PrivateKey privateKey = kf.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privBytes));

                ColorWallet wallet = new ColorWallet();
                wallet.privateKey = privateKey;
                wallet.publicKey = publicKey;
                return wallet;
            }
        } else {
            ColorWallet wallet = new ColorWallet();
            wallet.saveToFile();
            return wallet;
        }
    }

    public String getPublicKeyString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String sign(String data) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    public static boolean verify(String data, String signatureStr, String pubKeyStr) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        byte[] pubBytes = Base64.getDecoder().decode(pubKeyStr);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new java.security.spec.X509EncodedKeySpec(pubBytes));

        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(signatureStr));
    }
}
