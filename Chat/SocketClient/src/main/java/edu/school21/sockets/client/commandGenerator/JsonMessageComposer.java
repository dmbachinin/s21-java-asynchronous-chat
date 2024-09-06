package edu.school21.sockets.client.commandGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("messageComposer")
public class JsonMessageComposer implements MessageComposer {

    @Override
    public String compose(UserCommand response) {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(response);
//            System.out.println(json);
        } catch (Exception ignore) {}
        return json;
    }
}
