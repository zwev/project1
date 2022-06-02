package com.project1.services;

import com.project1.controller.UserController;
import com.project1.dao.UserDAO;
import com.project1.exceptions.UserNotFoundException;
import com.project1.model.Cart;
import com.project1.model.User;
import com.project1.util.UserCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceIm implements UserService{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired()
    UserDAO userDAO;
    @Autowired()
    UserCheck userCheck;
    @Autowired
    private HttpServletRequest req;
    @Autowired
    Cart cart;

    @Override
    public boolean isUser(int userId) {
        if(userDAO.existsById(userId)){
            return true;
        }
        else return false;
    }

    @Override
    public User getUser(int userId) {
        User u = userDAO.getReferenceById(userId);
        return u;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public List<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public boolean deleteUser(int userId) {
        if(userDAO.existsById(userId)){
            userDAO.deleteById(userId);
            return true;
        }
        else return false;
    }

    @Autowired @Override
    public boolean addUser(User user){
        if(userDAO.userExist(user.getEmail(), user.getUserName())){
            log.error("Username/password exist. Try something else");
            return false;
        }
        else {
            log.info("Adding user...");
            userDAO.save(user);
            return true;
        }
    }

    @Override
    public boolean updateUser(User newUser, int Id) {
        User oldUser = userDAO.getReferenceById(Id);
        if(userDAO.existsById(Id)){
            log.info("updating user info");
            oldUser.setUserName(newUser.getUserName());
            oldUser.setCountry(newUser.getCountry());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setCart(newUser.getCart());
            userDAO.save(oldUser);
            return true;
        }
        else{
            log.info("cannot update user");
            return false;
        }
    }

    public User login(String userName, String password) {
        User exists = userDAO.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException(String.format("No User with username = %s", userName)));
        // Maybe change the above exception to instead be a UnsuccessfulLoginException

        // Check that the given password matches the password in the User object
        // Pretend that they were successful


        HttpSession session = req.getSession();
        session.setAttribute("currentUser", exists);

        return exists;
    }

    public void logout() {

        HttpSession session = req.getSession(false);

        if(session == null) {
            // No one was logged in

            return;
        }

        session.invalidate();
    }

}
