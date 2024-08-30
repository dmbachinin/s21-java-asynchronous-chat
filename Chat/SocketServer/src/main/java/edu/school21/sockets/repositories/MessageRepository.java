package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;

import java.util.List;

public interface MessageRepository<T> extends CrudRepository<T>{
    List<Message> getMessagesByRoom(Long roomId, int page, int size);
}
