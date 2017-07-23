package com.pronovich.hotelbooking.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private final static String COMMAND_PARAMETER = "command";

    public Command initCommand(HttpServletRequest request) {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        Command command = null;
        try {
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
            command = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            //TODO log??
            // TODO command = Default command or redirect to error page???
        }
        return command;
    }
}
