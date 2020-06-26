package client;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client extends Thread {

    private long clientID;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private List<String> stringList;

    public Client(){
        try {
            socket = new Socket("localhost", 4004);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientID = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            while (!socket.isClosed()){
                String line = reader.readLine();
                stringList.add(line);
            }
            reader.close();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void upScore(){
        try {
            writer.write("+");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStringList() {
        return stringList;
    }

    public long getClientID() {
        return clientID;
    }
}
