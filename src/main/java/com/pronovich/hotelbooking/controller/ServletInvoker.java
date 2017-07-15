package com.pronovich.hotelbooking.controller;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.factory.CommandFactoryClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ServletInvoker extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        AbstractCommand executionCommand;
        executionCommand = new CommandFactoryClient().initCommand(request);
        executionCommand.execute(request, response);

    }
}
