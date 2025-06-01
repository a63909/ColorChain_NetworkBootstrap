package com.example.tamarashadow.core;

import java.io.Serializable;
import java.util.UUID;

public class ColorToken implements Serializable {
    private String id;
    private String evolutionCode;
    private String digitCode;
    private String colorHex;
    private int strength;
    private String ownerId;
    private String name = "";
    private ColorToken token;

    public int getPower() {
        return strength;
    }

    public String getEmoji() {
        if (isOrigin()) return "🌱";              // Прородитель
        if (isLbaP()) return "💎";               // LBU₽
        int power = this.strength;
        if (power >= 50) return "🔥";            // Сильный
        if (power >= 10) return "⚡";            // Усиленный
        return "🔵";                             // Базовый
    }


    public void setToken(ColorToken token) {
        this.token = token;
    }

    public ColorToken getToken() {
        return token;
    }

    public void evolve() {
        // Пример простой эволюции — увеличивает первую букву
        char[] chars = evolutionCode.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < 'z') {
                chars[i]++;
                break;
            }
        }
        this.evolutionCode = new String(chars);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ColorToken(String evolutionCode, String digitCode, String colorHex, String ownerId) {
        this.id = UUID.randomUUID().toString();
        this.evolutionCode = evolutionCode;
        this.digitCode = digitCode;
        this.colorHex = colorHex;
        this.strength = 0;
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }


    public String getId() {
        return id;
    }

    public String getEvolutionCode() {
        return evolutionCode;
    }

    public String getDigitCode() {
        return digitCode;
    }

    public String getColorHex() {
        return colorHex;
    }

    public int getStrength() {
        return strength;
    }

    public void incrementStrength() {
        this.strength++;
    }

    public boolean isLbaP() {
        return evolutionCode.equals("zzzzzzzzzz") && digitCode.equals("0000000000");
    }

    public boolean isOrigin() {
        return evolutionCode.equals("aaaaaaaaaa") && digitCode.equals("1111111111");
    }

    public String getDisplayType() {
        if (isOrigin()) return "Origin";
        if (isLbaP()) return "LBU₽";

        // Названия по силе
        if (strength < 5) return "Basic";
        if (strength < 10) return "Common";
        if (strength < 20) return "Uncommon";
        if (strength < 30) return "Rare";
        if (strength < 40) return "Epic";
        if (strength < 60) return "Mythic";
        if (strength < 80) return "Legendary";
        return "Celestial";
    }

    public static ColorToken createOriginToken(String ownerId) {{
            return new ColorToken("aaaaaaaaaa", "1111111111", "#ffffff", ownerId);
        }

    }

    @Override
    public String toString() {
        return "🧿 Token → " + getDisplayType() + "\n" +
                "ID: " + id + "\n" +
                "Evolution Code: " + evolutionCode + "\n" +
                "Digit Code: " + digitCode + "\n" +
                "Color: " + colorHex + "\n" +
                "Strength: " + strength + "\n";
    }
}
