package com.fedorov.chat_v3.Controllers;

// Импорт необходимых классов и пакетов
import com.fedorov.chat_v3.models.FeedMessage;
import com.fedorov.chat_v3.models.User;
import com.fedorov.chat_v3.models.UserResponse;
import com.fedorov.chat_v3.services.FeedService;
import com.fedorov.chat_v3.services.FileDataService;
import com.fedorov.chat_v3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// Аннотация @RestController указывает, что этот класс будет обрабатывать HTTP-запросы и возвращать JSON-ответы
@RestController
@CrossOrigin(origins = "http://localhost:63342") // Разрешает запросы из указанного источника
public class MainController {

    // Внедрение зависимостей для работы с пользователями, файлами и лентой
    @Autowired
    private UserService userService;

    @Autowired
    private FileDataService fileDataService;

    @Autowired
    private FeedService feedService;

    // Чтение токена из конфигурации приложения
    @Value("${spring.token}")
    private String token;

    // Обработка GET-запроса для получения списка пользователей
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        // Возвращает список пользователей в формате JSON
        return ResponseEntity.ok(userService.getUsers());
    }

    // Обработка GET-запроса для получения конкретного пользователя по ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser (@PathVariable int id) {
        // Возвращает информацию о пользователе по указанному ID
        return ResponseEntity.ok(userService.getUser (id));
    }

    // Обработка POST-запроса для регистрации нового пользователя
    @PostMapping("/registry")
    public ResponseEntity<HttpStatus> addUser (@Validated @RequestBody User user) {
        // Создает нового пользователя
        userService.createUser (user);
        // Возвращает статус 200 OK
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Обработка POST-запроса для входа пользователя
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        // Проверяет, успешна ли регистрация пользователя
        if (userService.signup(user.getUsername(), user.getPassword())) {
            // Получает информацию о пользователе из базы данных
            User user_base = userService.getUser (user.getUsername());
            // Создает ответ с информацией о пользователе и токене
            UserResponse response = new UserResponse(user_base.getId(), user_base.getUsername(), token, user_base.getFirstname(), user_base.getLastname());
            return ResponseEntity.ok(response);
        }
        // Возвращает статус 404 NOT FOUND, если регистрация не удалась
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Обработка POST-запроса для загрузки файла
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        // Загружает изображение для указанного пользователя
        fileDataService.uploadImage(file, username);
        // Возвращает сообщение об успешной загрузке файла
        return ResponseEntity.ok("{\"message\": \"Файл загружен успешно: " + file.getOriginalFilename() + "\"}");
    }

    // Обработка GET-запроса для загрузки изображения пользователя
    @GetMapping("/user_image/{username}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String username) {
        byte[] imageData = null;
        try {
            // Загружает изображение из файловой системы
            imageData = fileDataService.downloadImageFromFileSystem(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Возвращает изображение с соответствующим типом контента
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);
    }

    // Обработка GET-запроса для получения ленты сообщений
    @GetMapping("/feed")
    public ResponseEntity<?> getFeed() {
        // Возвращает ленту сообщений
        return ResponseEntity.ok(feedService.getFeeds());
    }

    // Обработка POST-запроса для создания нового сообщения в ленте
    @PostMapping("/feed")
    public ResponseEntity<?> createFeed(@RequestBody @Validated FeedMessage feedMessage) {
        // Создает новое сообщение в ленте и возвращает его
        return ResponseEntity.ok(feedService.createFeed(feedMessage));
    }
}