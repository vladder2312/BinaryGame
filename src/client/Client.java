package client;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {

    private final String nickname = "Andrew";
    private long clientID;
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;
    private String playerList;

    public Client(){
        playerList="";
        try {
            socket = new Socket("localhost", 4004);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            System.out.println("reading id...");
            clientID = Integer.parseInt(reader.readUTF());
            System.out.println("client id: "+clientID);
            writer.writeUTF(nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            while (!socket.isClosed()){
                playerList = reader.readUTF();
            }
            reader.close();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void upScore(){
        try {
            writer.writeUTF("+");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerList() {
        return playerList;
    }

    public long getClientID() {
        return clientID;
    }
}
