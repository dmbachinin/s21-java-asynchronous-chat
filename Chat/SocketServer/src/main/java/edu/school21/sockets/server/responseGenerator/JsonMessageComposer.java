package edu.school21.sockets.server.responseGenerator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("messageComposer")
public class JsonMessageComposer implements MessageComposer {

    @Override
    public String compose(Map<String, Object> data) {
        return null;
    }
}
