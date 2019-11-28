package edu.spbu.client_server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    static private Socket connection;
    static private DataOutputStream output;
    static private DataInputStream input;

    public static void main(String[] args){
        try{
            ServerSocket server = new ServerSocket(5678);
            while(true){
                System.out.println("Waiting for connection...");
                connection = server.accept();
                System.out.println("Connection accepted.");

                output = new DataOutputStream(connection.getOutputStream());
                System.out.println("DataOutputStream  created");

                input = new DataInputStream(connection.getInputStream());
                System.out.println("DataInputStream created");

                String filePath = receiveData(); //получает запрос клиента

                sendData(filePath); // отправляет ответ клиенту
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //отправляет ответ
    private static void sendData(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists()){
            try(FileReader fileRead = new FileReader(file)){
                output.flush();
                BufferedReader reader = new BufferedReader(fileRead);
                String text="";
                String i;
                while ((i = reader.readLine()) != null) {
                    text = text + i + "\r\n"; //посимвольно чтение файла
                }
                String response = "Отправляем текст из файла размером" + text.length() + "\r\n" +
                        "Connection: close\r\n\r\n";
                String result = response + text; //сервер выдает текст , который описан выше,  и текст из файла
                output.write(result.getBytes());//отправляем ответ

            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Файл не существует или ошибка");
            }
        } else{
            output.writeUTF("Искомого файла не существует");
        }


    }

    //получает запрос
    private static String receiveData(){

        String filePath;
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        System.out.println("Запрос принят");
        try{
            System.out.println("line");
            String line;
            if((line=reader.readLine())!=null) {
                System.out.println("line");
                String[] st = line.split(" ");
                if(st[1].length()>0){
                    filePath = st[1];
                    System.out.println("Запрос принят. Ищем файл: " + filePath);
                    return filePath;
                }

            }else System.out.println("ЧТо то сломалось");
        }catch(IOException e){
            e.printStackTrace();
        }
        return "empty";
    }
}
