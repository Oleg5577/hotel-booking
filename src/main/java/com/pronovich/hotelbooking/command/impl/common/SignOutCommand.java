package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public class SignOutCommand implements Command {

    //TODO path from properties???
    private static final String SIGN_IN_PAGE = "/jsp/home.jsp";

    private Receiver receiver;

    public SignOutCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        return new RequestResult(SIGN_IN_PAGE, NavigationType.REDIRECT);
    }
}
