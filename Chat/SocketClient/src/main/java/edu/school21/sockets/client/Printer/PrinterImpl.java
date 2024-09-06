package edu.school21.sockets.client.Printer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Component("printer")
public class PrinterImpl implements Printer {
    private Renderer renderer;
    private String separator = "_________________________\n";
    @Autowired
    public PrinterImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void printStartPage() {
        clear();
        renderer.render(
                separator +
                "1. Войти\n" +
                "2. Зарегистрироваться\n" +
                "3. Выйти\n" +
                separator
        );
    }

    @Override
    public void printChoseRoomPage() {
        clear();
        renderer.render(
                separator +
                "1. Создать комнату\n" +
                "2. Мои комнаты\n" +
                "3. Присоединиться к комнате\n" +
                "4. Выйти\n"+
                separator
        );
    }

    @Override
    public void printUserInfo(Map<String, Object> userInfo) {
        
    }

    @Override
    public void printChatRooms(List<Map<String, Object>> rooms) {
        StringJoiner stringJoiner =  new StringJoiner("\n");
        int number = 1;
        for (Map<String, Object> room : rooms) {
            stringJoiner.add(String.format("%d. %s", number++, room.get("name")));
        }
        stringJoiner.add(String.format("%d. Выйти", number));
        renderer.render(stringJoiner + "\n");
    }

    @Override
    public void printMessages(List<Map<String, Object>> messages) {
        if (!messages.isEmpty()) {
            StringJoiner stringJoiner =  new StringJoiner("\n");
            for (Map<String, Object> message : messages) {
                stringJoiner.add(String.format("%s: %s", message.get("senderName"), message.get("content")));
            }
            renderer.render(stringJoiner + "\n");
        }
    }

    @Override
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void printMessage(String message) {
        renderer.render(message);
    }
}
