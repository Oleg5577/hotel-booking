package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOutCommand  extends AbstractCommand {

    public SignOutCommand(Receiver receiver) {
        super(receiver);
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
