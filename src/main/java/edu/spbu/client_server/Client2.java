package edu.spbu.client_server;
import java.io.*;
import java.net.*;

public class Client2 {

    static private Socket connection;
    static private DataOutputStream output;
    static private DataInputStream input;


    public static void main(String[] args) throws IOException {
        try{
            connection = new Socket(InetAddress.getByName("ru.wikipedia.org"), 80);//имя хоста или ip адрес + порт
            output = new DataOutputStream(connection.getOutputStream());
            System.out.println("DataOutputStream  created");
            input = new DataInputStream(connection.getInputStream());
            System.out.println("DataInputStream created");
            sendData("ru.wikipedia.org");
            receiveData();

        } catch(IOException e){
            e.printStackTrace();
        }

    }

    //посылает запрос
    private static void sendData(String serverName) throws IOException {
        
        String s = "GET / HTTP/1.1\r\nHost:" + serverName +"\r\n\r\n";//пофиксить сервенэйм
        output.write(s.getBytes());
        output.flush();
        System.out.println("Запрос отправлен");
    }

    //получает ответ
    private static void receiveData() {
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
