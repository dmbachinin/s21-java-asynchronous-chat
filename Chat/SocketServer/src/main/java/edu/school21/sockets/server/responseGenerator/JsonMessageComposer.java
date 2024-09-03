package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.server.commandHandlers.CommandStatus;
import edu.school21.sockets.server.responseGenerator.responses.Response;
import jdk.jfr.snippets.Snippets;
import org.springframework.stereotype.Component;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("messageComposer")
public class JsonMessageComposer implements MessageComposer {

    private ObjectMapper objectMapper = new ObjectMapper();

    private String convertMapToJson(Map<String, Object> parameters) {
        try {
            return objectMapper.writeValueAsString(parameters);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String generate(CommandStatus status, String message, Map<String, Object> data) {
        data.put("status", status.toString());
        if (message != null) {
            data.put("message", message);
        }
        return convertMapToJson(data);
    }
}
