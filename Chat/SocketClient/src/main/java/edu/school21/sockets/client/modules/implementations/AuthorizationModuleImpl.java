package edu.school21.sockets.client.modules.implementations;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.commandGenerator.AvailableCommand;
import edu.school21.sockets.client.commandGenerator.CommandGenerator;
import edu.school21.sockets.client.commandGenerator.MessageComposer;
import edu.school21.sockets.client.modules.interfaces.InputModule;
import edu.school21.sockets.client.modules.interfaces.AuthorizationModule;
import edu.school21.sockets.client.requestHandles.Request;
import edu.school21.sockets.client.requestHandles.RequestHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component("authorizationModule")
public class AuthorizationModuleImpl implements AuthorizationModule {
    private RequestHandles requestHandles;
    private Printer printer;
    private InputModule inputModule;
    private MessageComposer messageComposer;
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    public AuthorizationModuleImpl(RequestHandles requestHandles,
                                   Printer printer,
                                   InputModule inputModule,
                                   MessageComposer messageComposer) {
        this.requestHandles = requestHandles;
        this.printer = printer;
        this.inputModule = inputModule;
        this.messageComposer = messageComposer;
    }

    public Long logIn(BufferedReader in, PrintWriter out) {
        Long userId = null;
        try {
            printer.printMessage("Введите данные для входа\n");
            Map<String, Class<?>> parameters = new TreeMap<>();
            parameters.put("email", String.class);
            parameters.put("password", String.class);
            Map<String, Object> data = inputModule.getParameters(scanner, parameters);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.LOG_IN, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            userId = request.getValue("userId", Integer.class).longValue();
        } catch (Exception ignore) {}
        return userId;
    }

    public Long singUp(BufferedReader in, PrintWriter out) {
        Long userId = null;
        try {
            printer.printMessage("Введите данные для регистрации\n");
            Map<String, Class<?>> parameters = new TreeMap<>();
            parameters.put("name", String.class);
            parameters.put("email", String.class);
            parameters.put("password", String.class);
            Map<String, Object> data = inputModule.getParameters(scanner, parameters);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.SING_UP, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            userId = request.getValue("userId", Integer.class).longValue();
        } catch (Exception ignore) {}
        return userId;
    }

    @Override
    public Long authorization(BufferedReader in, PrintWriter out) {
        Long userId = null;
        while (userId == null) {
            printer.printStartPage();
            String line = scanner.nextLine();
            if (line.equals("1")) {
                userId = logIn(in, out);
                if (userId == null) {
                    printer.printMessage("Неправильный логин или пароль\n");
                }
            } else if (line.equals("2")) {
                userId = singUp(in, out);
                if (userId == null) {
                    printer.printMessage("Пользователь с таким email уже зарегистрирован\n");
                }
            } else if (line.equalsIgnoreCase("exit") || line.equals("3")){
                break;
            }
        }
        return userId;
    }
}
