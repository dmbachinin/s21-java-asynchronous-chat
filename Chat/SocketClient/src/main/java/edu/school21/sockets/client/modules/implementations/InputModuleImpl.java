package edu.school21.sockets.client.modules.implementations;

import edu.school21.sockets.client.Printer.Printer;
import edu.school21.sockets.client.modules.interfaces.InputModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component("inputModule")
public class InputModuleImpl implements InputModule {

    private Printer printer;

    @Autowired
    public InputModuleImpl(Printer printer) {
        this.printer = printer;
    }

    public<T> T getValue(Scanner in, Class<T> clazz) {
        if (clazz == String.class) {
            return clazz.cast(in.nextLine());
        } else if (clazz == Integer.class) {
            return clazz.cast(in.nextInt());
        } else if (clazz == Double.class) {
            return clazz.cast(in.nextDouble());
        } else if (clazz == Long.class) {
            return clazz.cast(in.nextLong());
        } else if (clazz == Boolean.class) {
            return clazz.cast(in.nextBoolean());
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> getParameters(Scanner in, Map<String, Class<?>> parameters) {
        Map<String, Object> data = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : parameters.entrySet()) {
            printer.printMessage(entry.getKey() + ": ");
            data.put(entry.getKey(), getValue(in, entry.getValue()));
        }
        return data;
    }
}
