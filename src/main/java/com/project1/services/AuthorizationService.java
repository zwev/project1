package com.project1.services;

import com.project1.exceptions.NotAuthorizedException;
import com.project1.exceptions.NotLoggedInException;
import com.project1.model.Role;
import com.project1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthorizationService {

    @Autowired
    private HttpServletRequest req;
    public void guardByUserId(int userId) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("currentUser") == null) {
            throw new NotLoggedInException("Must be logged in to perform this action");
        }
        User currentUser = (User) session.getAttribute("currentUser");

        if(userId != currentUser.getId()) {
            if(currentUser.getRole() != Role.ADMIN) {
                throw new NotAuthorizedException("You are not permitted to perform this action on this resource");
            }
        }
    }
}