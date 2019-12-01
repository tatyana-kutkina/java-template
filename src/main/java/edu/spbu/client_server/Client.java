package edu.spbu.client_server;
import java.io.*;
import java.net.*;

public class Client implements Runnable {

    static private Socket connection;
    static private DataOutputStream output;
    static private DataInputStream input;

    public static void main(String[] args) throws IOException {
        try{
            Client client = new Client("127.0.0.1", 5678);
            client.sendData("Server");
            client.receiveData();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //конструктор клиента
    public Client(String serverName, int port ) throws IOException {
        connection = new Socket(InetAddress.getByName(serverName),port);

        output = new DataOutputStream(connection.getOutputStream());
        System.out.println("DataOutputStream  created");

        input = new DataInputStream(connection.getInputStream());
        System.out.println("DataInputStream created");
    }

    @Override
    public void run(){

    }

    //посылает запрос
    private void sendData(String serverName) throws IOException {
        
        String s = "GET /test.html HTTP/1.1\r\nHost:" + serverName +"\r\n\r\n";//пофиксить сервенэйм
        output.write(s.getBytes());
        output.flush();
        System.out.println("Запрос отправлен");
    }

    //получает ответ
    private void receiveData() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("Работаем...\n");
            String s = reader.readLine();
            if(s!=null)
                System.out.println("Ответ: ");
            while(s!=null){
                System.out.println(s);
                s=reader.readLine();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
