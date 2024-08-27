package edu.school21.sockets.services;

public interface UsersService {
    boolean signUp(String email, String password);
    boolean logIn(String email, String password);
}
