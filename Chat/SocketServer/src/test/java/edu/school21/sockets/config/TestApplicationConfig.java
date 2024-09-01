package edu.school21.sockets.config;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.*;
import edu.school21.sockets.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Bean
    ChatRoomsRepository<ChatRoom> chatRoomsRepository() {
        return new ChatRoomsRepositoryImpl(testDataSource());
    }

    @Bean
    UsersRepository<User> usersRepository() {
        return new UsersRepositoryImpl(testDataSource());
    }

    @Bean
    MessageRepository<Message> messageRepository() {
        return new MessageRepositoryImpl(testDataSource());
    }

    @Bean
    ChatRoomService chatRoomService() {
        return new ChatRoomServiceImpl(chatRoomsRepository(), usersRepository());
    }

    @Bean
    MessageService messageService() {
        return new MessageServiceImpl(messageRepository(),
                chatRoomsRepository(), usersRepository());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UsersService usersService() {
        return new UsersServiceImpl(usersRepository(), passwordEncoder());
    }
}
