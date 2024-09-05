package edu.school21.sockets.client.Printer;

import java.util.List;
import java.util.Map;

public interface Printer {
    void printStartPage();
    void printChoseRoomPage();

    void printUserInfo(Map<String, Object> userInfo);

    void printChatRooms(List<Map<String, Object>> rooms);

    void printMessages(List<Map<String, Object>> messages);

    void printMessage(String message);
}
