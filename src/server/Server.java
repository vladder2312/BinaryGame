package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Server extends Thread {

    private ServerSocket socket;
    private String playerList;
    private final LinkedList<ServerHandler> connections;

    public Server() {
        connections = new LinkedList<>();
        playerList = "";
        try {
            socket = new ServerSocket(4004);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int clientId = connections.size() + 1;
                Socket newConnection = socket.accept();
                ServerHandler handler = new ServerHandler(newConnection, this, clientId);
                connections.add(handler);
                sendDataAll();
                handler.getWriter().writeUTF("Player " + clientId + ": " + 0);
                handler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDataAll() {
        try {
            for (ServerHandler handler : connections) {
                handler.getWriter().writeUTF(playerList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerList() {
        playerList = "";
        for (ServerHandler handler : connections) {
            playerList+=(handler.getNickname()+"(" + handler.getClientId() + "): " + handler.getScore()+"\n");
        }
    }

    public void deleteUser(ServerHandler handler){
        connections.remove(handler);
    }
}
