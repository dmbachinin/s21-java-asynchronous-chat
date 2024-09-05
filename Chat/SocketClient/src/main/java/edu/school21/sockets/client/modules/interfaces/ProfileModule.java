package edu.school21.sockets.client.modules.interfaces;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ProfileModule {
    public Long choosingRoom(Long userId, BufferedReader in, PrintWriter out);
}
