package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public  abstract class AbstractCommand {

    private Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * Abstract method for execute all commands
     * @param request - HttpServletRequest object
     * @return - RequestResult object
     */
    public abstract RequestResult execute(HttpServletRequest request);
}
