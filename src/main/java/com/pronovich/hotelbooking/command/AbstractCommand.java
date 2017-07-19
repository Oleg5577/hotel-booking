package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public  abstract class AbstractCommand {

    private Receiver receiver;

    AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCommand that = (AbstractCommand) o;

        return receiver != null ? receiver.equals(that.receiver) : that.receiver == null;
    }

    @Override
    public int hashCode() {
        return receiver != null ? receiver.hashCode() : 0;
    }
}
