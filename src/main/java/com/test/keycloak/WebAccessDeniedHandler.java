package com.test.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException, ServletException {

        res.setStatus(HttpStatus.FORBIDDEN.value());

        if(e instanceof AccessDeniedException){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null && ((User)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("USER"))){
                req.setAttribute("msg","접근원한 없는 사용자입니다.");
                req.setAttribute("nextPage","/main");
            }
        }

        req.getRequestDispatcher("/err/denied-page").forward(req,res);
    }
}
