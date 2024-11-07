package com.fedorov.chat_v2.Controllers;

import com.fedorov.chat_v2.TestResponse;
import com.fedorov.chat_v2.models.User;
import com.fedorov.chat_v2.models.UserResponse;
import com.fedorov.chat_v2.repositories.UserRepository;
import com.fedorov.chat_v2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class MainController {

    @Autowired
    private UserService userService;

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
}
