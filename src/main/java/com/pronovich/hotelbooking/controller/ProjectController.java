package com.pronovich.hotelbooking.controller;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.dao.connectionpool.ConnectionPool;
import com.pronovich.hotelbooking.command.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ProjectController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         AbstractCommand command = new CommandFactory().initCommand(request);
        RequestResult requestResult = command.execute(request);

        String page = requestResult.getPage();
        NavigationType navigationType = requestResult.getNavigationType();

        if (navigationType == NavigationType.FORWARD ) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        } else if (navigationType == NavigationType.REDIRECT) {
            response.sendRedirect(page);
        } else {
            LOGGER.error("Wrong navigation page type");
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getPool().closeAllConnections();
        super.destroy();
    }
}
