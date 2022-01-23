package com.example.passwordmanager.security.listener;

import com.example.passwordmanager.entity.User;
import com.example.passwordmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.passwordmanager.service.UserService.MAX_FAILED_ATTEMPTS;

@Component
public class AuthenticationFailureListener extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private IUserService userService;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        Optional<User> user = userService.findByEmail(email);
        exception = new BadCredentialsException("Could not login. Wrong credentials");

        if (user.isPresent()) {
            User u = user.get();

            if (u.isActive()) {
                if (u.getLoginAttempts() == MAX_FAILED_ATTEMPTS+1) {
                    exception = new LockedException("You failed to login more than "+MAX_FAILED_ATTEMPTS+" times." +
                            " Your account has been locked for 24h for safety reasons");

                    userService.lockUser(u);
                } else {
                    userService.updateFailedAttempts(u);
                }
            } else {
                if(!userService.isUserStillLocked(u)){
                    userService.resetAttempts(u);
                    userService.updateFailedAttempts(u);

                }
            }

            super.setDefaultFailureUrl("/login/?error");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
