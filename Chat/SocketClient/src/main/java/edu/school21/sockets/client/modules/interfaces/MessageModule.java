package edu.school21.sockets.client.modules.interfaces;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface MessageModule {
    void chatCorrespondence(Long userId, Long roomId, BufferedReader in, PrintWriter out);
}