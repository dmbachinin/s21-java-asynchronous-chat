package edu.school21.sockets.client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8083);
        Scanner clientIn = new Scanner(System.in);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
            String line = "";
            while (!"exit".equals(line)) {
                System.out.println("Введите команду:");
                line = clientIn.nextLine();
                out.println(line);
                System.out.println("Сервер сказал:");
                System.out.println(in.readLine());
            }
        } finally {
            socket.close();
        }


    }
}