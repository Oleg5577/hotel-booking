package com.pronovich.hotelbooking.command.impl;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SignUpCommand implements Command {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeat-password";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phone-number";

    private static final String DEFAULT_USER_ROLE = "user";

    private static final String SIGN_UP_PAGE = "jsp/signup.jsp";
    private static final String SIGN_IN_PAGE = "jsp/signin.jsp";

    private Receiver receiver;

    public SignUpCommand(Receiver receiver) {
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
        String repeatPassword = request.getParameter(REPEAT_PASSWORD_PARAM).trim();
        String name = request.getParameter(NAME_PARAM).trim();
        String surname = request.getParameter(SURNAME_PARAM).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAM).trim();

        HashMap<String, String> requestValues = new HashMap<>();

        requestValues.put("email", email);
        requestValues.put("password", password);
        requestValues.put("repeatPassword", repeatPassword);
        requestValues.put("name", name);
        requestValues.put("surname", surname);
        requestValues.put("phoneNumber", phoneNumber);
        requestValues.put("role", DEFAULT_USER_ROLE);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.SIGN_UP, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("correctValues", content.getParameters());
            requestResult = new RequestResult(SIGN_UP_PAGE, NavigationType.FORWARD);
        } else {
            requestResult = new RequestResult(SIGN_IN_PAGE, NavigationType.FORWARD);
        }
        return requestResult;
    }
}
