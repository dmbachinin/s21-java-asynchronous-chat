package edu.school21.sockets.client.modules.implementations;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.commandGenerator.AvailableCommand;
import edu.school21.sockets.client.commandGenerator.CommandGenerator;
import edu.school21.sockets.client.commandGenerator.MessageComposer;
import edu.school21.sockets.client.modules.interfaces.InputModule;
import edu.school21.sockets.client.modules.interfaces.MessageModule;
import edu.school21.sockets.client.requestHandles.Request;
import edu.school21.sockets.client.requestHandles.RequestHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Thread.sleep;

@Component("messageModule")
public class MessageModuleImpl implements MessageModule {

    private RequestHandles requestHandles;
    private Printer printer;
    private InputModule inputModule;
    private MessageComposer messageComposer;
    private Scanner scanner = new Scanner(System.in);
    private volatile boolean keepRunning = true;

    @Autowired
    public MessageModuleImpl(RequestHandles requestHandles,
                             Printer printer,
                             InputModule inputModule,
                             MessageComposer messageComposer) {
        this.requestHandles = requestHandles;
        this.printer = printer;
        this.inputModule = inputModule;
        this.messageComposer = messageComposer;
    }

    public boolean sendMessage(String content, Long userId, Long roomId, BufferedReader in, PrintWriter out) {
        try {
            Map<String, Object> data =  new HashMap<>();
            data.put("roomId", roomId);
            data.put("userId", userId);
            data.put("content", content);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.SEND_MESSAGE, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            return request.getStatus().equalsIgnoreCase("OK");
        } catch (Exception ignore) {}
        return false;
    }

    public List<Map<String, Object>> getMessages(Long roomId, int page, int size, BufferedReader in, PrintWriter out) {
        try {
            Map<String, Object> data =  new HashMap<>();
            data.put("roomId", roomId);
            data.put("size", size);
            data.put("page", page);
            String json = messageComposer.compose(
                    CommandGenerator.generate(AvailableCommand.GET_MESSAGE, data)
            );
            out.println(json);
            Request request = requestHandles.processing(in.readLine());
            List<Map<String, Object>> list = (List<Map<String, Object>>) request.getData().get("messages");
            return list == null ? new ArrayList<>() : list;
        } catch (Exception ignore) {}
        return null;
    }

    public static List<Map<String, Object>> getNewMessages(List<Map<String, Object>> messagesList,
                                                           List<Map<String, Object>> newMessagesList) {
        Set<Object> existingIds = new HashSet<>();
        for (Map<String, Object> message : messagesList) {
            Object id = message.get("id");
            if (id != null) {
                existingIds.add(id);
            }
        }

        List<Map<String, Object>> newMessages = new ArrayList<>();

        for (Map<String, Object> message : newMessagesList) {
            Object id = message.get("id");
            if (id != null && !existingIds.contains(id)) {
                newMessages.add(message);
            }
        }
        return newMessages;
    }

    private void mergeMessagesList(List<Map<String, Object>> messagesList,
                                   List<Map<String, Object>> newMessagesList) {
        messagesList.addAll(newMessagesList);
    }

    @Override
    public void chatCorrespondence(Long userId, Long roomId, BufferedReader in, PrintWriter out) {
        keepRunning = true;
        Thread threadForPrintMessage = getThreadForPrintMessage(roomId, in, out);
        threadForPrintMessage.start();
        printer.printMessage("Введите /exit, чтобы выйти\n");
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("/exit") || line.equals("/выход")){
                break;
            }
            boolean isSend = sendMessage(line, userId, roomId, in, out);
            if (!isSend) {
                printer.printMessage("Ошбика отправки сообщения.\n");
            }
        }
        keepRunning = false;

    }

    private Thread getThreadForPrintMessage(Long roomId, BufferedReader in, PrintWriter out) {
        List<Map<String, Object>> messageList = new ArrayList<>();
        Thread threadForPrintMessage = new Thread(() -> {
            while (keepRunning) {
                List<Map<String, Object>> messagesFormServer = getMessages(roomId, 1 , 30, in, out);
                List<Map<String, Object>> newMessageList = getNewMessages(messageList, messagesFormServer);
                printer.printMessages(newMessageList);
                mergeMessagesList(messageList, newMessageList);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignore) {}
            }

        });
        return threadForPrintMessage;
    }
}
