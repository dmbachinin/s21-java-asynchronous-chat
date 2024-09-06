package edu.school21.sockets.client.modules.interfaces;

import java.io.*;
import java.util.Optional;

public interface AuthorizationModule {
    Long authorization(BufferedReader in, PrintWriter out);
}
