package com.project1.services;

import com.project1.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    public boolean isUser(int userId);

    public User getUser(int userId);

    public List<User> getUsers();

    public List<User> getUserByEmail(String email);

    public boolean deleteUser(int userId);

    public boolean addUser(User user);

    public boolean updateUser(User user, int id);

    public void logout();
    public User login(String username, String password);

    boolean updateUserCart(User user, int id);
}
