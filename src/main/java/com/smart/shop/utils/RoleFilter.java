package com.smart.shop.utils;

import com.smart.shop.enums.Role;
import com.smart.shop.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();

        if(path.contains("/login") || path.contains("/register")){
            chain.doFilter(request,response);
            return;
        }

        if(session == null || session.getAttribute("USER") == null){
            response.setContentType("application/json");
            response.getWriter().write("error : non authentifie");
            ((HttpServletResponse) response).setStatus(401);
            return;
        }

        User user = (User) session.getAttribute("USER");

        if(path.contains("/admin") && user.getRole() != Role.ADMIN){
            response.setContentType("application/json");
            response.getWriter().write("error :  acces refuse");
            ((HttpServletResponse) response).setStatus(403);
            return;
        }

        chain.doFilter(request,response);

    }
}
