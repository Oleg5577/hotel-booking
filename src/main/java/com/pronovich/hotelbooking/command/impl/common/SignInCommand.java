package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SignInCommand implements Command {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_PARAM = "user";

    //TODO page path from properties??;
    private static final String SIGN_IN_PAGE = "/jsp/signin.jsp";
    private static final String WELCOME_PAGE = "/jsp/welcome.jsp";

    private Receiver receiver;

    public SignInCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(EMAIL_PARAM, email);
        requestValues.put(PASSWORD_PARAM, password);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.SIGN_IN, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("correctValues", content.getRequestParameters());
            requestResult = new RequestResult(SIGN_IN_PAGE, NavigationType.FORWARD);
        } else {
            User user = (User) content.getSessionAttributes().get(USER_PARAM);
            if (user == null) {
                request.setAttribute("wrongValues", wrongValues);
                requestResult = new RequestResult(SIGN_IN_PAGE, NavigationType.FORWARD);
            } else {
                request.getSession().setAttribute(USER_PARAM, user);
                requestResult = new RequestResult(WELCOME_PAGE, NavigationType.REDIRECT);
            }
        }
        return requestResult;
    }
}
