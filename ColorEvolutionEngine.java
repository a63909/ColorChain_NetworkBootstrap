// ColorEvolutionEngine.java
package com.example.tamarashadow.core;

public class ColorEvolutionEngine {

    // Эволюция строки: aaaaaaaaaa → aaaaaaaaab → ... → zzzzzzzzzz
    public static String evolve(String currentCode) {
        if (currentCode == null || currentCode.length() != 10) return currentCode;

        char[] chars = currentCode.toCharArray();

        for (int i = 9; i >= 0; i--) {
            if (chars[i] < 'z') {
                chars[i]++;
                break;
            } else {
                chars[i] = 'a';
            }
        }
        return new String(chars);
    }

    // Преобразуем код в HEX-цвет (чем ближе к z, тем темнее)
    public static String getColorFromCode(String code) {
        if (code == null || code.length() != 10) return "#FFFFFF";

        int darkness = 0;
        for (char c : code.toCharArray()) {
            darkness += (c - 'a');
        }
        int level = Math.min(255, (int) (255 - (darkness * 1.2))); // 0...255 → белый → чёрный
        return String.format("#%02x%02x%02x", level, level, level);
    }

    // Проверка на достижение lbu₽-уровня
    public static boolean isMaxLevel(String code) {
        return "zzzzzzzzzz".equals(code);
    }
}
