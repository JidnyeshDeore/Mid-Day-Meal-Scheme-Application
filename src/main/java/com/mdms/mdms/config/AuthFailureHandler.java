package com.mdms.mdms.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
                


                // at the tome of email verfication
                if(exception instanceof DisabledException){

                    HttpSession session=request.getSession();
                    session.setAttribute("message", "School email is unverfied..");
                

                response.sendRedirect("/login");
        }else{

            response.sendRedirect("/login?error=true");
    }
            }
}
