package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.User;

public class UserInfoResponse implements Response<User> {
    @Override
    public String generate(User user) {
        return "Привет " + user.getName() + "\n";
    }
}
