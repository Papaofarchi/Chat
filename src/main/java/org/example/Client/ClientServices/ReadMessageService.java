package org.example.Client.ClientServices;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadMessageService extends Thread {
    private final BufferedReader in;
    private final ClientGeneralService client;

    public ReadMessageService(BufferedReader in, ClientGeneralService client) {
        this.in = in;
        this.client = client;
    }
    @SneakyThrows
    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                if (message.equals("stop server")) {
                   client.downService();
                    break;
                }
                System.out.println(message);
            }
        } catch (IOException e) {
            client.downService();
        }

    }
}
