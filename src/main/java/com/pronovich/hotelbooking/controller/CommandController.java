package com.pronovich.hotelbooking.controller;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.impl.common.SignOutCommand;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.command.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class CommandController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        handleRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = new CommandFactory().initCommand(request);
        RequestResult requestResult = command.execute(request);
        if (isSignOutCommand(command)) {
            request.getSession().invalidate();
        }
        navigateToPage(requestResult, request, response);
    }

    //TODO check result
    private boolean isSignOutCommand(Command executionCommand) {
        return executionCommand instanceof SignOutCommand;
    }

    private void navigateToPage(RequestResult requestResult, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = requestResult.getPage();
        NavigationType navigationType = requestResult.getNavigationType();

        if (navigationType == NavigationType.FORWARD ) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        } else if (navigationType == NavigationType.REDIRECT) {
            response.sendRedirect(page);
        } else {
            //TODO log???
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getPool().closeAllConnections();
        super.destroy();
    }
}
