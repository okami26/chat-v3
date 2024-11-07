package com.fedorov.chat_v3.Controllers;

import com.fedorov.chat_v3.models.User;
import com.fedorov.chat_v3.models.UserResponse;
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

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileDataService fileDataService;

    @Value("${spring.token}")
    private String token;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam String token) {

        if (token != null && token.equals(this.token)) {

            return ResponseEntity.ok(userService.getUsers());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/registry")
    public ResponseEntity<HttpStatus> addUser(@Validated @RequestBody User user) {

        userService.createUser(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        if (userService.signup(user.getUsername(), user.getPassword())) {
            User user_base = userService.getUser(user.getUsername());
            UserResponse response = new UserResponse(user_base.getId(), user_base.getUsername(), user_base.getPassword(), token);

            return ResponseEntity.ok(response);
        };

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        String upload_image = fileDataService.uploadImage(file, username);
        return ResponseEntity.ok("{\"message\": \"Файл загружен успешно: " + file.getOriginalFilename() + "\"}");
    }

    @GetMapping("/user_image/{username}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String username) {


        byte[] imageData = null;
        try {
            imageData = fileDataService.downloadImageFromFileSystem(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
                    .body(imageData);

    }
}
