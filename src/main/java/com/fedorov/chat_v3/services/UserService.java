package com.fedorov.chat_v3.services;

import com.fedorov.chat_v3.advice.UserNameException;
import com.fedorov.chat_v3.advice.UserNotFoundException;
import com.fedorov.chat_v3.models.User;
import com.fedorov.chat_v3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

// Аннотация @Service указывает, что данный класс является сервисом и содержит бизнес-логику
@Service
public class UserService {

    // Внедрение зависимости репозитория пользователей
    @Autowired
    private UserRepository userRepository;

    // Константа для хранения пути к директории пользователей
    private static final String USER_DIRECTORY = "src/main/resources/static/FileSystem/Users";

    // Получение списка всех пользователей
    public List<User> getUsers() {
        return userRepository.findAll(); // Возвращает всех пользователей из репозитория
    }

    // Получение пользователя по ID
    public User getUser (int id) {
        Optional<User> user = userRepository.findById(id); // Ищет пользователя по ID
        return user.orElseThrow(UserNotFoundException::new); // Если не найден, выбрасывает исключение
    }

    // Получение пользователя по имени пользователя
    public User getUser (String username) {
        Optional<User> user = userRepository.findByUsername(username); // Ищет пользователя по имени
        return user.orElseThrow(UserNotFoundException::new); // Если не найден, выбрасывает исключение
    }

    // Создание нового пользователя
    public User createUser (User user) {
        // Проверка на существование пользователя с таким же именем
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserNameException(); // Если пользователь с таким именем уже существует, выбрасывает исключение
        }

        // Создание директории для пользователя
        String folder = USER_DIRECTORY + "/" + user.getUsername();
        try {
            // Создаем директорию, если она не существует
            Files.createDirectories(Path.of(folder)); // Используем createDirectories для создания всех необходимых родительских директорий
        } catch (IOException e) {
            // Если произошла ошибка при создании директории, выбрасываем исключение
            throw new RuntimeException("Не удалось создать директорию для пользователя: " + user.getUsername(), e);
        }

        // Сохранение пользователя в репозитории
        return userRepository.save(user); // Сохраняет пользователя и возвращает его
    }

    // Метод для проверки входа пользователя
    public boolean signup(String username, String password) {
        // Проверяет, существует ли пользователь с указанным именем и паролем
        if (userRepository.findByUsernameAndPassword(username, password).isPresent()) {
            return true; // Если существует, возвращает true
        }
        throw new UserNotFoundException(); // Если не найден, выбрасывает исключение
    }

    // Обновление информации о пользователе
    public User updateUser (Integer id, User user) {
        Optional<User> userOptional = userRepository.findById(id); // Ищет пользователя по ID
        if (userOptional.isPresent()) {
            user.setId(id); // Устанавливает ID для обновляемого пользователя
        }
        return userRepository.save(user); // Сохраняет обновленного пользователя и возвращает его
    }

    // Удаление пользователя по ID
    public void deleteUser (Integer id) {
        userRepository.deleteById(id); // Удаляет пользователя из репозитория по ID
    }
}
