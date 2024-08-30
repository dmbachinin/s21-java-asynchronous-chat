package edu.school21.sockets.server.responseGenerator;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Map;

public interface MessageComposer {
    String compose(Map<String, Object> data);
}

