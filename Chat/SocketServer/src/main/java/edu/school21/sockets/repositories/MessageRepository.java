package edu.school21.sockets.repositories;

import java.util.List;

public interface MessageRepository<T> extends CrudRepository<T>{
    List<T> getMessagesForCurrentRoom(Long roomId, Integer count);
}
