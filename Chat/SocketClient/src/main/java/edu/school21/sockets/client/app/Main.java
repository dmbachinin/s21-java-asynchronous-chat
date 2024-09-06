package edu.school21.sockets.client.app;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.config.ApplicationConfig;
import edu.school21.sockets.client.modules.implementations.MessageModuleImpl;
import edu.school21.sockets.client.modules.interfaces.AuthorizationModule;
import edu.school21.sockets.client.modules.interfaces.ProfileModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8083);
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Printer printer = context.getBean("printer", Printer.class);
        AuthorizationModule authorizationModule = context.getBean("authorizationModule", AuthorizationModule.class);
        ProfileModule profileModule = context.getBean("profileModule", ProfileModule.class);
        MessageModuleImpl messageModule = context.getBean("messageModule", MessageModuleImpl.class);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
            printer.printMessage(in.readLine() + "\n");
            main:
            while (true) {
                Long userID = authorizationModule.authorization(in, out);
                Long lastVisitRoom = profileModule.getLastVisitRoom(userID, in, out);
                List<Map<String, Object>> message = messageModule.getMessages(lastVisitRoom, 1, 30, in, out);
                if (!message.isEmpty()) {
                    printer.printMessage("Сообщения из последней посещенной вами комнаты: \n");
                    printer.printMessages(message);
                }
                if (userID == null) {
                    break;
                }
                while (true) {
                    Long roomId = profileModule.choosingRoom(userID, in, out);
                    if (roomId == null) {
                        continue main;
                    }
                    printer.clear();
                    messageModule.chatCorrespondence(userID, roomId, in, out);
                }
            }
        } finally {
            socket.close();
        }


    }
}