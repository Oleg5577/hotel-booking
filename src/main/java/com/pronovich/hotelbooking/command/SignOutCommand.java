package com.pronovich.hotelbooking.command;


import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutCommand extends AbstractCommand {

    private static final String SIGN_IN_PAGE = "/jsp/signin.jsp";

    SignOutCommand(Receiver receiver) {
        super(receiver);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return SIGN_IN_PAGE;
    }
}
