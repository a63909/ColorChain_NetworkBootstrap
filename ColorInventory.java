package com.example.tamarashadow.core;

import android.content.Context;
import android.util.Log;

import com.example.tamarashadow.core.ColorChain;
import com.example.tamarashadow.core.ColorToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ColorInventory {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String TOKEN_FILE_NAME = "tokens.json";

    public static void saveTokens(Context context) {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                context.openFileOutput(TOKEN_FILE_NAME, Context.MODE_PRIVATE))) {
            gson.toJson(ColorChain.getTokens(), writer);
            Log.d("ColorInventory", "[ТАМАРА] 💾 Токены сохранены в " + TOKEN_FILE_NAME);
        } catch (IOException e) {
            Log.e("ColorInventory", "[ТАМАРА] ❌ Ошибка при сохранении токенов: " + e.getMessage());
        }
    }

    public static void loadTokens(Context context) {
        try (InputStreamReader reader = new InputStreamReader(
                context.openFileInput(TOKEN_FILE_NAME))) {
            Type listType = new TypeToken<List<ColorToken>>() {}.getType();
            List<ColorToken> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                ColorChain.setTokens(loaded);
                Log.d("ColorInventory", "[ТАМАРА] 🔄 Токены загружены из " + TOKEN_FILE_NAME);
            }
        } catch (IOException e) {
            Log.e("ColorInventory", "[ТАМАРА] ⚠ Не удалось загрузить токены: " + e.getMessage());
        }
    }

    public static ColorToken findByName(String name) {
        for (ColorToken token : ColorChain.getTokens()) {
            if (token.getName().equalsIgnoreCase(name)) {
                return token;
            }
        }
        return null;
    }

    public static ColorToken findByHash(String hash) {
        for (ColorBlock block : ColorChain.getChain()) {
            if (block.getBlockHash().equals(hash)) {
                return block.getToken(); // если ты добавил getToken()
            }
        }

        return null;
    }

    public static void listAllTokens() {
        List<ColorToken> tokens = ColorChain.getTokens();
        if (tokens == null || tokens.isEmpty()) {
            Log.d("ColorInventory", "[ТАМАРА] ❗ В инвентаре нет токенов.");
            return;
        }

        Log.d("ColorInventory", "[ТАМАРА] 🔎 Список токенов:");
        for (ColorToken token : tokens) {
            Log.d("ColorInventory", token.toString());
        }
    }

    public static List<ColorToken> getAllTokens() {
        return new ArrayList<>(ColorChain.getTokens());
    }

    public static int getTokenCount() {
        return ColorChain.getTokens().size();
    }

    public static void addToken(ColorToken token) {
        ColorChain.addToken(token);
    }
}
