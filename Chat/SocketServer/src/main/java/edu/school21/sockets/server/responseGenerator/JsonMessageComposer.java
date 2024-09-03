package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.server.communication.ServerResponse;
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
    public String generate(ServerResponse response) {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(response);
        } catch (Exception ignore) {}
        return json;
    }
}
