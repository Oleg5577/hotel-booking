package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOutCommand implements Command {

    @Override
    public Receiver getReceiver() {
        //TODO throw new UnsupportedOperationException ????
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            request.getSession().invalidate();
        }
        return new RequestResult(ProjectConstants.HOME_PAGE, NavigationType.REDIRECT);
    }
}
