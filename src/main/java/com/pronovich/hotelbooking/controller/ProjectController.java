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

    /**
     * Handle requests of doGet and doPost methods
     * @param request -  HttpServletRequest object
     * @param response - HttpServletResponse object
     * @throws ServletException - if navigateToPage exception
     * @throws IOException - if navigateToPage exception
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AbstractCommand command = new CommandFactory().initCommand(request);
        RequestResult requestResult = command.execute(request);
        navigateToPage(request, response, requestResult);
    }

    /**
     * Define a navigation type (forward or sendRedirect) and navigate to page
     * @param request - HttpServletRequest object
     * @param response - HttpServletResponse object
     * @param requestResult - result of the command
     * @throws ServletException - if exception in forward method
     * @throws IOException - if exception in forward or sendRedirect method
     */
    private void navigateToPage(HttpServletRequest request, HttpServletResponse response, RequestResult requestResult) throws ServletException, IOException {
        String page = requestResult.getPage();
        NavigationType navigationType = requestResult.getNavigationType();

        if (navigationType == NavigationType.FORWARD) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        } else if (navigationType == NavigationType.REDIRECT) {
            response.sendRedirect(page);
        } else {
            LOGGER.error("Wrong navigation page type");
        }
    }

    /**
     * In the method all connections are closed
     */
    @Override
    public void destroy() {
        ConnectionPool.getPool().closeAllConnections();
        super.destroy();
    }
}
