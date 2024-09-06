package edu.school21.sockets.client.Printer;

import org.springframework.stereotype.Component;

@Component("renderer")
public class ConsoleRenderer implements Renderer{
    @Override
    public void render(String message) {
        System.out.print(message);
    }
}
