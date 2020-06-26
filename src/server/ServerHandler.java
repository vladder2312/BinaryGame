package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerHandler extends Thread{

    private String nickname;
    private int clientId;
    private int score;
    private Socket socket;
    private Server server;
    private DataInputStream reader;
    private DataOutputStream writer;

    public ServerHandler(Socket socket, Server server, int clientId){
        score=0;
        try{
            this.clientId=clientId;
            this.server=server;
            this.socket = socket;
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            System.out.println("Sending ID "+clientId);
            writer.writeUTF(String.valueOf(clientId));
            nickname = reader.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()){
                try {
                    sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                server.updatePlayerList();
                server.sendDataAll();
                try {
                    if (reader.readUTF().equals("+")) {
                        score++;
                    }
                } catch (SocketException e){
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                    System.out.println("Connection "+clientId+" closed");
                    server.deleteUser(this);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public int getClientId() {
        return clientId;
    }

    public int getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }
}
