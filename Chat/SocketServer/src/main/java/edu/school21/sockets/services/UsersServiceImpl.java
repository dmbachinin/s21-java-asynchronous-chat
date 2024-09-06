package edu.school21.sockets.services;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Optional<User> signUp(String email, String name, String password) {
        if (usersRepository.findByEmail(email).isPresent()
            || password == null || email == null) {
            return Optional.empty();
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        String passwordHash = passwordEncoder.encode(password);
        newUser.setPasswordHash(passwordHash);
        usersRepository.save(newUser);

        return Optional.of(newUser);
    }

    @Override
    public Optional<User> logIn(String email, String password) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (!user.isPresent() || password == null) {
            return Optional.empty();
        }
        String passwordHash = user.get().getPasswordHash();
        return passwordEncoder.matches(password, passwordHash) ? user : Optional.empty();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}