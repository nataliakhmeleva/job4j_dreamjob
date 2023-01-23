package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oUserRepositoryTest {
    static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    void whenSave() {
        User user = new User(1, "1@ya.ru", "Ivan", "meow123");
        var expected = sql2oUserRepository.save(user);
        var savedUser = sql2oUserRepository.findById(user.getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void whenDoNotSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
    }

    @Test
    void whenFindByEmailAndPassword() {
        User user = new User(1, "1@ya.ru", "Ivan", "meow123");
        var expected = sql2oUserRepository.save(user);
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void whenSaveOnlyTheFirstUniqueEmail() {
        User user = new User(1, "1@ya.ru", "Ivan", "meow123");
        User anotherUser = new User(2, "1@ya.ru", "Stepan", "321go");
        sql2oUserRepository.save(user);
        assertThat(sql2oUserRepository.save(anotherUser)).isEmpty();
    }

}