package com.example.tamarashadow.core;

public class ColorNodeManager {

    private static ColorNodeManager instance;
    private ColorNode currentNode;

    private ColorNodeManager() {}

    public static synchronized ColorNodeManager getInstance() {
        if (instance == null) {
            instance = new ColorNodeManager();
        }
        return instance;
    }

    public void setCurrentNode(ColorNode node) {
        this.currentNode = node;
    }

    public ColorNode getCurrentNode() {
        return currentNode;
    }
}
