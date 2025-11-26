package com.smart.shop.utils;

import com.smart.shop.enums.Role;
import com.smart.shop.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import java.io.IOException;

@Component
public class RoleFilter implements Filter {
    private final PathMatcher pathMatcher;

    public RoleFilter(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

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

        if((path.contains("/admin") || path.contains("/product") || path.contains("/api/v1/product/delete")) && user.getRole() != Role.ADMIN){
            response.setContentType("application/json");
            response.getWriter().write("error :  acces refuse");
            ((HttpServletResponse) response).setStatus(403);
            return;
        }
//        // todo : update and delete only for admin to do
        if(path.matches("/api/v1/client/info/\\d+") || path.matches("/api/v1/client/update/\\d+") || path.matches("/api/v1/client/delete/\\d+")){
            String[] parts = path.split("/");
            Integer id = Integer.parseInt(parts[parts.length-1]);

            if(user.getRole() != Role.ADMIN && (user.getClient() == null || user.getClient().getId() != id)){
                response.setContentType("application/json");
                response.getWriter().write("error :  acces refuse");
                ((HttpServletResponse) response).setStatus(403);
                return;
            }
        }
        chain.doFilter(request,response);

    }
}
