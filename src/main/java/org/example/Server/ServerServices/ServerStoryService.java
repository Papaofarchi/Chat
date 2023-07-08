package org.example.Server.ServerServices;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ServerStoryService {
    private final LinkedList<String> story = new LinkedList<>();

    public void addMessageToStory(String message) {
        if (!message.contains("joined the server")) {
            if (story.size() >= 10) {
                story.removeFirst();
                story.add(message);
            } else {
                story.add(message);
            }
        }
    }

    @SneakyThrows
    public void printStory(BufferedWriter writer) {
        StringBuilder sb = new StringBuilder();
        int maxLength = 0;
        if (story.size() > 0) {
            for (String oneMessage : story) {
                if (oneMessage.length() > maxLength) {
                    maxLength = oneMessage.length();
                }
            }
            sb.append("*".repeat(maxLength + 2)).append("\n");
            for (String oneMessage : story) {
                sb.append(String.format("%-" + (maxLength + 1) + "s* \n", oneMessage));
            }
            sb.append("*".repeat(maxLength + 2)).append("\n");
            String displayedStory = sb.toString();
            try {
                writer.write("Chat history is: \n");
                writer.write(displayedStory + "\n");
                writer.flush();
            } catch (IOException ignored) {
            }
        }
    }
}
