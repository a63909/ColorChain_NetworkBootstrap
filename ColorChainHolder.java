package com.example.tamarashadow.core;

public class ColorChainHolder {
    private static final ColorChain instance = new ColorChain();

    public static ColorChain getInstance() {
        return instance;
    }
}
