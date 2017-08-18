package com.pronovich.hotelbooking.filter;

import java.io.IOException;
import java.util.ArrayList;
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

import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.entity.Role;
import com.pronovich.hotelbooking.entity.User;

@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private final ArrayList<String> commonUserCommands = new ArrayList<>();
    private final ArrayList<String> clientCommands = new ArrayList<>();
    private final ArrayList<String> adminCommands = new ArrayList<>();

    /**
     * @param fConfig FilterConfig
     * @throws ServletException exception
     */
    public void init(FilterConfig fConfig) throws ServletException {
        initCommonUserCommands();
        initClientCommands();
        initAdminCommands();
    }

    private void initCommonUserCommands() {
        commonUserCommands.add("edit_user_info");
    }

    private void initClientCommands() {
        clientCommands.add("find_info_for_client_account");
        clientCommands.add("cancel_order_by_client");
        clientCommands.add("add_room_request");
        clientCommands.add("cancel_request_by_client");
        clientCommands.add("cancel_request_by_client");
        adminCommands.add("cancel_order_by_client");
    }

    private void initAdminCommands() {
        adminCommands.add("find_info_for_admin_account");
        adminCommands.add("find_rooms_according_request");
        adminCommands.add("create_order");
        adminCommands.add("cancel_request_by_admin");
        adminCommands.add("cancel_order_by_admin");
        adminCommands.add("change_order_status_to_paid");
        adminCommands.add("change_order_status_to_checked_in");
        adminCommands.add("change_order_status_to_checked_out");
        adminCommands.add("issue_invoice");
    }

    /**
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     * @throws IOException      exception
     * @throws ServletException exception
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String URI = getFullURL(httpServletRequest);

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");

        if ( !isAuthorizedUser(user) ) {
            if (isUriAllowedForAllAuthorizedUser(URI) || isUriAllowedOnlyForClient(URI) || isUriAllowedOnlyForAdmin(URI) ) {
                httpServletResponse.sendRedirect(ProjectConstants.SIGN_IN_PAGE);
                return;
            }
        } else if ( !isClient(user) && isUriAllowedOnlyForClient(URI)){
            httpServletResponse.sendRedirect(ProjectConstants.HOME_PAGE);
            return;
        } else if (!isAdmin(user) && isUriAllowedOnlyForAdmin(URI)) {
            httpServletResponse.sendRedirect(ProjectConstants.HOME_PAGE);
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    private static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    private boolean isUriAllowedForAllAuthorizedUser(String URI) {
        return isCommonUserCommands(URI);
    }

    private boolean isUriAllowedOnlyForClient(String URI) {
        return URI.contains("/jsp/client") || isClientCommand(URI);
    }

    private boolean isUriAllowedOnlyForAdmin(String URI) {
        return URI.contains("/jsp/admin") || isAdminCommand(URI);
    }

    private boolean isAuthorizedUser(User user) {
        return user != null && user.getRole() != null;
    }

    private boolean isClient(User user) {
        return user.getRole() == Role.CLIENT;
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    private boolean isCommonUserCommands(String URI) {
        for (String s : commonUserCommands) {
            if (URI.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isClientCommand(String URI) {
        for (String s : clientCommands) {
            if (URI.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdminCommand(String URI) {
        for (String s : adminCommands) {
            if (URI.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
