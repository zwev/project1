package com.project1.advice;

import com.project1.annotations.Authorized;
import com.project1.exceptions.NotAuthorizedException;
import com.project1.exceptions.NotLoggedInException;
import com.project1.model.Role;
import com.project1.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    private HttpServletRequest req;

    @Around("@annotation(authorized)") // PointCut
    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("currentUser") == null) {
            throw new NotLoggedInException("Must be logged in to perform this action");
        }

        User currentUser = (User) session.getAttribute("currentUser");
        Role currentRole = currentUser.getRole();
        List<Role> allowedRoles = Arrays.asList(authorized.allowedRoles());

        if(!allowedRoles.contains(currentRole)) {
            throw new NotAuthorizedException("You are not authorized to perform this action");
        }
        return pjp.proceed(pjp.getArgs());
    }
}