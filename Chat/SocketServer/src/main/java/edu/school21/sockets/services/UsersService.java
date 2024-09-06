package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {
    Optional<User> signUp(String email, String name, String password);
    Optional<User> logIn(String email, String password);

    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
}
