package com.example.tamarashadow.core;

import com.example.tamarashadow.core.ColorDTO;
import java.security.MessageDigest;
import java.util.List;

public class ColorHashGenerator {

    public static String generateColorHash(List<ColorDTO> colors, int x, int y, String phrase, long timestamp) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 1. Цветовая ДНК
            for (ColorDTO color : colors) {
                digest.update((byte) color.getRed());
                digest.update((byte) color.getGreen());
                digest.update((byte) color.getBlue());
            }


            // 2. Координаты
            digest.update(intToBytes(x));
            digest.update(intToBytes(y));

            // 3. Время
            digest.update(longToBytes(timestamp));

            // 4. Фраза вызова
            if (phrase != null) {
                digest.update(phrase.getBytes());
            }

            // 5. Ключевые числа
            digest.update("1001".getBytes());
            digest.update("a639091".getBytes());
            digest.update("2409".getBytes());

            // 6. Получаем хэш
            byte[] hashBytes = digest.digest();

            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }

            return hashString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ColorDTO fromCode(String code) {
        int r = 0, g = 0, b = 0;

        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);
            if (i % 3 == 0) r += ch;
            else if (i % 3 == 1) g += ch;
            else b += ch;
        }

        r = (r * 7) % 256;
        g = (g * 11) % 256;
        b = (b * 13) % 256;

        return new ColorDTO(r, g, b, 0, 0); // x=0, y=0 по умолчанию

    }


    private static byte[] intToBytes(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte) value
        };
    }

    private static byte[] longToBytes(long value) {
        return new byte[] {
                (byte)(value >>> 56),
                (byte)(value >>> 48),
                (byte)(value >>> 40),
                (byte)(value >>> 32),
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte) value
        };
    }
}
