package edu.spbu.client_server;
import java.io.*;
import java.net.*;

public class Client2 {

    static private Socket connection;
    static private DataOutputStream output;
    static private DataInputStream input;

    public static void main(String[] args) {
        try{
            while (true){
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);

                output = new DataOutputStream(connection.getOutputStream());
                System.out.println("DataOutputStream  created");
                input = new DataInputStream(connection.getInputStream());
                System.out.println("DataInputStream created");
                sendData(); // посылает запрос
                receiveData(); // получает ответ
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //посылает запрос
    private static void sendData() throws IOException {
        output.flush();
        String s = "GET /home/tatyana/test.txt";
        output.write(s.getBytes());
        System.out.println("Запрос отправлен");

    }

    //получает ответ
    private static void receiveData() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("работаем");
            String s = reader.readLine();
            if(s!=null)
                System.out.println("Хоть чтото работает");
            else
                System.out.println("Что-то не так");
            while(s!=null){
                System.out.println(s);
                s=reader.readLine();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
