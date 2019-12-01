package edu.spbu.client_server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Server2 {

    static private Socket connection;
    static private DataOutputStream output;
    static private DataInputStream input;

    public static void main(String[] args){
        try{
            ServerSocket server = new ServerSocket(5678);

                System.out.println("Waiting for connection...");

                connection = server.accept();
                System.out.println("Connection accepted.");

                output = new DataOutputStream(connection.getOutputStream());
                System.out.println("DataOutputStream  created");

                input = new DataInputStream(connection.getInputStream());
                System.out.println("DataInputStream created");

                String filePath = receiveData(); //получает запрос клиента

                sendData(filePath); // отправляет ответ клиенту

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
                StringBuilder text = new StringBuilder();
                String i,content;
                i=reader.readLine();

                //чтение из файла
                while(i!=null){
                    text.append(i);
                    i=reader.readLine();
                }

                content = text.toString();
                String message="HTTP/1.1 200 OK\r\n" +
                        "Server: Kakoi-to server\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Connection: close\r\n\r\n" +content;
                output.write(message.getBytes());//отправляем ответ
                output.close();
            }
            catch(IOException e){
                e.printStackTrace();
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
            String line;
            if((line=reader.readLine())!=null) {
                //System.out.println(line);
                String[] st = line.split(" ");
                System.out.println(Arrays.toString(st));
                if(st[1].length()>0){
                    filePath = st[1].substring(1);
                    System.out.println("Запрос принят. Ищем файл: " + filePath);
                    String str;
                    while((str=reader.readLine()).length()!=0)
                        System.out.println(str);
                    return filePath;
                }
            }else System.out.println("Что то сломалось");

        }catch(IOException e){
            e.printStackTrace();
        }
        return "empty";
    }
}
