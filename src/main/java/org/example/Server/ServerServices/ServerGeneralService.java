package org.example.Server.ServerServices;

import lombok.SneakyThrows;
import org.example.Server.ServerApplication;

import java.io.*;
import java.net.Socket;

public class ServerGeneralService extends Thread {
    private final Socket socket;
    private final BufferedReader input;
    private final BufferedWriter output;

    @SneakyThrows
    public ServerGeneralService(Socket socket) {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ServerApplication.story.printStory(output);
        start();
    }

    @SneakyThrows
    @Override
    public void run() {
        String message;
        try {
            message = input.readLine();
            try {
                output.write(message + "\n"); // message for chat
                output.flush();
            } catch (IOException ignored) {
            }
            try {
                while (true) {
                    message = input.readLine();
                    if (message.equals("stop server")) {
                        this.downService();
                        break;
                    }
                    System.out.println(message); // message for server log
                    ServerApplication.story.addMessageToStory(message);
                    for (ServerGeneralService oneMessage : ServerApplication.serverList) {
                        oneMessage.send(message);
                    }

                }
            } catch (IOException ignored) {
            }
        } catch (IOException e) {
            this.downService();
        }
    }

    @SneakyThrows
    private void send(String message) {
        try {
            output.write(message + "\n");
            output.flush();
        } catch (IOException ignored) {
        }
    }

    @SneakyThrows
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                input.close();
                output.close();
            }
            for (ServerGeneralService oneMessage : ServerApplication.serverList) {
                if (oneMessage.equals(this)) {
                    oneMessage.interrupt();
                }
                ServerApplication.serverList.remove(this);
            }
        } catch (IOException ignored) {
        }

    }
}
