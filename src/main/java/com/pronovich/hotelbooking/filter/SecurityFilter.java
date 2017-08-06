package com.pronovich.hotelbooking.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pronovich.hotelbooking.entity.Role;
import com.pronovich.hotelbooking.entity.User;

@WebFilter(urlPatterns = {"/jsp/client/*", "/jsp/admin/*"})
public class SecurityFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String URI = httpServletRequest.getRequestURI();

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");

        //TODO add pattern /controller except signin and signout
        if (URI.startsWith("/jsp/client") && user == null || user.getRole() != Role.CLIENT) {
            httpServletResponse.sendRedirect("/jsp/signin.jsp");
            return;
        } else if (URI.startsWith("/jsp/admin") && user.getRole() != Role.ADMIN /*|| URI.startsWith("/controller?command")*/) {
            httpServletResponse.sendRedirect("/jsp/home.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
