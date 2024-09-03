package edu.school21.sockets.server.responseGenerator.responses;

import edu.school21.sockets.models.User;

public class UserInfoResponse {
    public static String generateWelcomeMessage(User user) {
        return "Привет " + user.getName() + "\n";
    }
}
