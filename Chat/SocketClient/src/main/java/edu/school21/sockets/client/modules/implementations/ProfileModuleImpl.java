package edu.school21.sockets.client.modules.implementations;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.commandGenerator.AvailableCommand;
import edu.school21.sockets.client.commandGenerator.CommandGenerator;
import edu.school21.sockets.client.commandGenerator.MessageComposer;
import edu.school21.sockets.client.modules.interfaces.InputModule;
import edu.school21.sockets.client.modules.interfaces.ProfileModule;
import edu.school21.sockets.client.requestHandles.Request;
import edu.school21.sockets.client.requestHandles.RequestHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

@Component("profileModule")
public class ProfileModuleImpl implements ProfileModule {

    private RequestHandles requestHandles;
    private Printer printer;
    private InputModule inputModule;
    private MessageComposer messageComposer;
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    public ProfileModuleImpl(RequestHandles requestHandles,
                                   Printer printer,
                                   InputModule inputModule,
                                   MessageComposer messageComposer) {
        this.requestHandles = requestHandles;
        this.printer = printer;
        this.inputModule = inputModule;
        this.messageComposer = messageComposer;
    }

    public Long creteRoom(Long userId, BufferedReader in, PrintWriter out) {
        Long roomId = null;
        try {
            printer.printMessage("Введите данные для создания комнаты: \n");
            Map<String, Class<?>> parameters = new TreeMap<>();
            parameters.put("name", String.class);
            parameters.put("description", String.class);
            Map<String, Object> data = inputModule.getParameters(scanner, parameters);
            data.put("userId", userId);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.CREATE_ROOM, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            roomId = request.getValue("roomId", Integer.class).longValue();
            data = new HashMap<>();
            data.put("roomId", roomId);
            data.put("userId", userId);
            json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.JOIN_THE_ROOM, data)
            );
            out.println(json);
            requestHandles.processing(in.readLine());
        } catch (Exception ignore) {}
        return roomId;
    }

    public Long choseRoom(Long userId, BufferedReader in, PrintWriter out) {
        Long roomId = null;
        try {
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.GET_ROOMS, new HashMap<>())
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            List<Map<String, Object>> rooms = (List<Map<String, Object>>) request.getData().get("rooms");
            if (rooms.isEmpty()) {
                printer.printMessage("Пока комнат нет. \n");
                return null;
            }
            printer.printMessage("Список комнат: \n");
            printer.printChatRooms(rooms);
            int choseNumber = -1;
            while (choseNumber < 0 || choseNumber > rooms.size()) {
                printer.printMessage("Введите номер комнаты из списка: \n");
                try {
                    choseNumber = scanner.nextInt() - 1;
                } catch (Exception e) {
                    printer.printMessage("Некорректный ввод! Введите число.\n");
                    scanner.nextLine();
                }
            }
            if (choseNumber == rooms.size()) {
                return null;
            }
            Map<String, Object> data = rooms.get(choseNumber);
            data.put("userId", userId);
            json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.JOIN_THE_ROOM, data)
            );
            out.println(json);
            request = requestHandles.processing(in.readLine());
            if (request.getStatus().equalsIgnoreCase("OK")) {
                roomId = ((Integer)data.get("roomId")).longValue();
            }
        } catch (Exception ignore) {}
        return roomId;
    }


    public Long choseUserRoom(Long userId, BufferedReader in, PrintWriter out) {
        Long roomId = null;
        try {
            Map<String, Object> data =  new HashMap<>();
            data.put("userId", userId);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.GET_USER_ROOMS, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            List<Map<String, Object>> rooms = (List<Map<String, Object>>) request.getData().get("rooms");
            if (rooms.isEmpty()) {
                printer.printMessage("Ваш список комнат пуст. \n");
                return null;
            }
            printer.printMessage("Ваш список комнат: \n");
            printer.printChatRooms(rooms);
            int choseNumber = -1;
            while (choseNumber < 0 || choseNumber > rooms.size()) {
                printer.printMessage("Введите номер комнаты из списка: \n");
                try {
                    choseNumber = scanner.nextInt() - 1;
                } catch (Exception e) {
                    printer.printMessage("Некорректный ввод! Введите число.\n");
                    scanner.nextLine();
                }
            }
            if (choseNumber == rooms.size()) {
                return null;
            }
            roomId = ((Integer)rooms.get(choseNumber).get("roomId")).longValue();
        } catch (Exception ignore) {}
        return roomId;
    }

    @Override
    public Long choosingRoom(Long userId, BufferedReader in, PrintWriter out) {
        Long roomId = null;
        while (roomId == null) {
            printer.printChoseRoomPage();
            String line = scanner.nextLine();
            if (line.equals("1")) {
                roomId = creteRoom(userId, in, out);
            } else if (line.equals("2")) {
                roomId = choseUserRoom(userId, in, out);
            } else if (line.equals("3")) {
                roomId = choseRoom(userId, in, out);
            } else if (line.equalsIgnoreCase("exit") || line.equals("4")){
                break;
            }
            scanner.nextLine();
        }
        return roomId;
    }
}
