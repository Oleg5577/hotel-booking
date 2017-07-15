package com.pronovich.hotelbooking.factory;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;

import javax.servlet.http.HttpServletRequest;

public class CommandFactoryClient {

    private final static String COMMAND_PARAMETER = "command";

    public AbstractCommand initCommand(HttpServletRequest request) {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        AbstractCommand command = null;
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            command = type.getCommand();
        } catch (IllegalArgumentException e) {
            //TODO log??
            e.printStackTrace();
        }
        return command;
    }
}
