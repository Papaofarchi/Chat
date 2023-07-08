package org.example.Client.ClientServices;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteMessageService extends Thread {
    private final BufferedWriter out;
    private final BufferedReader inputFromConsole;
    private final String nickname;
    private final ClientGeneralService client;

    public WriteMessageService(BufferedWriter out,
                               BufferedReader inputFromConsole,
                               String nickname,
                               ClientGeneralService client) {
        this.out = out;
        this.inputFromConsole = inputFromConsole;
        this.nickname = nickname;
        this.client = client;

    }
    @SneakyThrows
    @Override
    public void run() {

        while (true) {
            String userMessage;
            try {
                Date time = new Date();
                SimpleDateFormat dataTimeFormat = new SimpleDateFormat("HH:mm");
                String displayedTime = dataTimeFormat.format(time);
                userMessage = inputFromConsole.readLine();
                if (userMessage.equals("stop server")) {
                    out.write("stop server" + "\n");
                    client.downService();
                    break;
                } else {
                    out.write(displayedTime + " " + nickname + ": " + userMessage + "\n");
                }
                out.flush();
            } catch (IOException e) {
                client.downService();
            }
        }
    }
}
