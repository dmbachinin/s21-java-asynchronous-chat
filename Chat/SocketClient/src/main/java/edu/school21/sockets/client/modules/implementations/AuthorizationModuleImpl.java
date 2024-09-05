package edu.school21.sockets.client.modules.implementations;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.modules.InputModule;
import edu.school21.sockets.client.requestHandles.RequestHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component("authorizationModule")
public class AuthorizationModule {
    private RequestHandles requestHandles;
    private Printer printer;
    private InputModule printer;

    @Autowired
    public AuthorizationModule(RequestHandles requestHandles,
                               Printer printer) {
        this.requestHandles = requestHandles;
        this.printer = printer;
    }

    public Optional<Long> logIn(BufferedReader in, PrintWriter out) {
        Long userId = null;
        String line;
        try {
            printer.printMessage("Введите данные для входа\n");
            printer.printMessage("email: ");
            while((line = in.readLine()) != null) {
                printer.printMessage("Введите данные для входа\n");
                printer.printMessage("email: ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
