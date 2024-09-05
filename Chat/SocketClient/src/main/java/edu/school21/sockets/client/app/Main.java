package edu.school21.sockets.client.app;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.config.ApplicationConfig;
import edu.school21.sockets.client.modules.interfaces.AuthorizationModule;
import edu.school21.sockets.client.modules.interfaces.ProfileModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8083);
        Scanner clientIn = new Scanner(System.in);
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Printer printer = context.getBean("printer", Printer.class);
        AuthorizationModule authorizationModule = context.getBean("authorizationModule", AuthorizationModule.class);
        ProfileModule profileModule = context.getBean("profileModule", ProfileModule.class);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
            printer.printMessage(in.readLine() + "\n");
            while (true) {
                Long userID = authorizationModule.authorization(in, out);
                if (userID == null) {
                    break;
                }
                Long roomId = profileModule.choosingRoom(userID, in, out);
                if (roomId == null) {
                    continue;
                }

            }
        } finally {
            socket.close();
        }


    }
}