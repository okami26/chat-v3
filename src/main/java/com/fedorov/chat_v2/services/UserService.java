package com.fedorov.chat_v2.services;

import com.fedorov.chat_v2.advice.UserNameException;
import com.fedorov.chat_v2.advice.UserNotFoundException;
import com.fedorov.chat_v2.models.User;
import com.fedorov.chat_v2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
