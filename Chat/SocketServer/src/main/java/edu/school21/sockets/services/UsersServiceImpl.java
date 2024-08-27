package edu.school21.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService{

    private final UsersRepository<User> usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository<User> usersRepository,
                            PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean signUp(String email, String password) {
        if (usersRepository.findByEmail(email).isPresent()) {
            return false;
        }
        User newUser = new User();
        newUser.setEmail(email);
        String passwordHash = passwordEncoder.encode(password);
        newUser.setPasswordHash(passwordHash);
        usersRepository.save(newUser);
        return true;

    }

    @Override
    public boolean logIn(String email, String password) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (!user.isPresent()) {
            return false;
        }
        String passwordHash = user.get().getPasswordHash();
        return passwordEncoder.matches(password, passwordHash);
    }
}
