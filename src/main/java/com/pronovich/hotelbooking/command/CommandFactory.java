package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.common.DefaultCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    private final static String COMMAND_PARAMETER = "command";

    public AbstractCommand initCommand(HttpServletRequest request) {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        AbstractCommand command;
        try {
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
            command = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("The command [" + commandName + "] is not exists");
            command = new DefaultCommand(null);
        }
        return command;
    }
}
