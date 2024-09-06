package edu.school21.sockets.config;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.*;
import edu.school21.sockets.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
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
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource); // Компонент для отмены транзакций
    }

    @Bean
    ChatRoomsRepository<ChatRoom> chatRoomsRepository(DataSource dataSource) {
        return new ChatRoomsRepositoryImpl(dataSource);
    }

    @Bean
    UsersRepository<User> usersRepository(DataSource dataSource) {
        return new UsersRepositoryImpl(dataSource);
    }

    @Bean
    MessageRepository<Message> messageRepository(DataSource dataSource) {
        return new MessageRepositoryImpl(dataSource);
    }

    @Bean
    ChatRoomService chatRoomService(DataSource dataSource) {
        return new ChatRoomServiceImpl(chatRoomsRepository(dataSource), usersRepository(dataSource));
    }

    @Bean
    MessageService messageService(DataSource dataSource) {
        return new MessageServiceImpl(messageRepository(dataSource),
                chatRoomsRepository(dataSource), usersRepository(dataSource));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UsersService usersService(DataSource dataSource) {
        return new UsersServiceImpl(usersRepository(dataSource), passwordEncoder());
    }

}
