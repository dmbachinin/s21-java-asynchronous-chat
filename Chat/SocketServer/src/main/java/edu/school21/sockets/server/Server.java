package edu.school21.sockets.server;

import edu.school21.sockets.server.CommandProcessor.CommandProcessor;
import edu.school21.sockets.server.communication.ServerResponse;
import edu.school21.sockets.server.responseGenerator.MessageComposer;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.ExecutorService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

@Component("server")
public class Server implements Runnable {
    private final CommandProcessor processor;
    private final MessageComposer messageComposer;
    private ServerSocket serverSocket;
    private volatile boolean running = true;
    private final ExecutorService executorService;

    @Autowired
    public Server(
            @Value("${server.port:8081}") int port,
            @Value("${server.threadPool:10}") int threadPool,
            CommandProcessor processor,
            MessageComposer messageComposer) throws IOException {
        this.processor = processor;
        this.messageComposer = messageComposer;
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(threadPool);
    }

    @Override
    public void run() {
        try {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("ECЬ Клиент");
                executorService.execute(()->{handleClient(clientSocket);});
            }
        } catch (Exception e) {
            if (running) {
                e.printStackTrace();
            }
        } finally {
            stop();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in =
                     new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out =
                     new PrintWriter(clientSocket.getOutputStream(),true)) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(inputLine)) {
                    break;
                }
                ServerResponse response = processor.handeCommand(inputLine);
                String responseJson = messageComposer.generate(response);
                out.println(responseJson);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public int getPort() {
         return serverSocket.getLocalPort();
     }

    public String getAddress() {
        return serverSocket.getInetAddress().getHostAddress();
    }
}
