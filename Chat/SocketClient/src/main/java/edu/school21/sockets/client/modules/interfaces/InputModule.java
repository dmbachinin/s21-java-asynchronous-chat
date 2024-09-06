package edu.school21.sockets.client.modules.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public interface InputModule {
    Map<String, Object> getParameters(Scanner in, Map<String, Class<?>> parameters);
}
