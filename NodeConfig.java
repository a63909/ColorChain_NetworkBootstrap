package com.example.tamarashadow.core;

public class NodeConfig {
    public static String BOOTSTRAP_IP = "192.168.0.197";
    public static int BOOTSTRAP_PORT = 1001;

    public static String getBootstrapUrl() {
        return "ws://" + BOOTSTRAP_IP + ":" + BOOTSTRAP_PORT;
    }
}
