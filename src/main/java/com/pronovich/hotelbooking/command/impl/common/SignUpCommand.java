package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SignUpCommand  extends AbstractCommand {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeatPassword";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";
    private static final String ROLE_PARAM = "role";
    private static final String DEFAULT_ROLE = "client";
    private static final String WRONG_VALUES_PARAM = "wrongValues";
    private static final String REQUEST_VALUES_PARAM = "requestValues";

    public SignUpCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = putRequestParametersInRequestContent(request);
        getReceiver().action(CommandType.SIGN_UP, content);
        return defineRequestResult(request, content);
    }

    private RequestContent putRequestParametersInRequestContent(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();
        String repeatPassword = request.getParameter(REPEAT_PASSWORD_PARAM).trim();
        String name = request.getParameter(NAME_PARAM).trim();
        String surname = request.getParameter(SURNAME_PARAM).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAM).trim();

        HashMap<String, String> requestParameters = new HashMap<>();

        requestParameters.put(EMAIL_PARAM, email);
        requestParameters.put(PASSWORD_PARAM, password);
        requestParameters.put(REPEAT_PASSWORD_PARAM, repeatPassword);
        requestParameters.put(NAME_PARAM, name);
        requestParameters.put(SURNAME_PARAM, surname);
        requestParameters.put(PHONE_NUMBER_PARAM, phoneNumber);
        requestParameters.put(ROLE_PARAM, DEFAULT_ROLE);

        return new RequestContent(requestParameters);
    }

    private RequestResult defineRequestResult(HttpServletRequest request, RequestContent content) {
        Map<String, String> wrongValues = content.getWrongValues();
        if ( !wrongValues.isEmpty()) {
            request.setAttribute(WRONG_VALUES_PARAM, wrongValues);
            request.setAttribute(REQUEST_VALUES_PARAM, content.getRequestParameters());
            return new RequestResult(ProjectConstants.SIGN_UP_PAGE, NavigationType.FORWARD);
        }
        return new RequestResult(ProjectConstants.SIGN_IN_PAGE, NavigationType.REDIRECT);
    }
}
