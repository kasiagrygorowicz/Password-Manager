package com.example.passwordmanager.security.listener;

import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessListener implements AuthenticationSuccessHandler {


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private IUserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = "/dashboard";
        System.out.println("ehere");
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getLoginAttempts());
        if(user.getLoginAttempts()!=0){
            userService.resetAttempts(user);
            System.out.println("deleting attempts");
        }


        if (response.isCommitted()) {
            System.out.println(
                    "Unable to redirect to "
                            + url);
            return;
        }


        redirectStrategy.sendRedirect(request, response, url);
    }
}
