package org.example.Client.Users;

import org.example.Client.ClientServices.ClientGeneralService;

import java.util.Scanner;

public class SecondClientApplication {
    private static String ipAddress;
    private static int port;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter IP: ");
        ipAddress = scanner.next();
        System.out.println("Enter your port: ");
        port = scanner.nextInt();
        new ClientGeneralService(ipAddress, port);

    }
}
