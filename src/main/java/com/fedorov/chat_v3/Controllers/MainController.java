package com.fedorov.chat_v3.Controllers;

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

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileDataService fileDataService;

    @Autowired
    private FeedService feedService;

    @Value("${spring.token}")
    private String token;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {



            return ResponseEntity.ok(userService.getUsers());



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
            UserResponse response = new UserResponse(user_base.getId(), user_base.getUsername(), token, user_base.getFirstname(), user_base.getLastname());

            return ResponseEntity.ok(response);
        };

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        fileDataService.uploadImage(file, username);
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

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed() {

        return ResponseEntity.ok(feedService.getFeeds());

    }

    @PostMapping("/feed")
    public ResponseEntity<?> createFeed(@RequestBody @Validated FeedMessage feedMessage) {

        return ResponseEntity.ok(feedService.createFeed(feedMessage));

    }
}
