package org.example.Client.ClientServices;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class ClientGeneralService extends Thread {
    private ClientGeneralService client;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputFromConsole;
    private String nickname;


    @SneakyThrows
    public ClientGeneralService(String idAddress, int port) {
        try {
            this.socket = new Socket(idAddress, port);
        } catch (IOException e) {
            System.out.println("Incorrect data");
        }
        try {
            inputFromConsole = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.setNickname();
            new ReadMessageService(in, client).start();
            new WriteMessageService(out, inputFromConsole, nickname, client).start();
        } catch (IOException e) {
            ClientGeneralService.this.downService();
        }
    }

    @SneakyThrows
    private void setNickname() {
        System.out.println("Enter your nickname: ");
        try {
            nickname = inputFromConsole.readLine();
            out.write("Welcome to chat " + nickname + "\n");
            out.write(nickname + " joined the server \n");
            out.flush();
        } catch (IOException e) {
            System.out.println("Fuck your nickname");
        }
    }

    public void setClient(ClientGeneralService client) {
        this.client = client;
    }

    @SneakyThrows
    public void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }
}


























