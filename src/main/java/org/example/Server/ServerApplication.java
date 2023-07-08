package org.example.Server;

import lombok.SneakyThrows;
import org.example.Server.ServerServices.ServerGeneralService;
import org.example.Server.ServerServices.ServerStoryService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerApplication {
    public static final int port = 7777;
    public static LinkedList<ServerGeneralService> serverList = new LinkedList<>();
    public static ServerStoryService story;


    @SneakyThrows
    public static void main(String[] args) {
        ServerSocket server = new ServerSocket(port);
        story = new ServerStoryService();
        System.out.println("Server started successfully");
        try {
            while (true) {
                Socket socket = server.accept();
                serverList.add(new ServerGeneralService(socket));
            }
        } finally {
            server.close();
        }
    }
}
