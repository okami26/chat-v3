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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }

    public User createUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserNameException();
        }

        String folder = "src/main/resources/static/FileSystem/Users" + "/" + user.getUsername();

        try {
            Files.createDirectory(Path.of(folder));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userRepository.save(user);
    }

    public boolean signup(String username, String password) {
        if (userRepository.findByUsernameAndPassword(username, password).isPresent()) {

            return true;
        }
        throw new UserNotFoundException();
    }

    public User updateUser(Integer id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
        }
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
