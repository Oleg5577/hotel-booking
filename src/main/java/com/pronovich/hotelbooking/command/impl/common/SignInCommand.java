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

public class SignInCommand  extends AbstractCommand {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String USER_PARAM = "user";
    private static final String WRONG_VALUES_PARAM = "wrongValues";
    private static final String REQUEST_VALUES_PARAM = "requestValues";

    public SignInCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = putRequestParametersInRequestContent(request);
        getReceiver().action(CommandType.SIGN_IN, content);
        return defineRequestResult(request, content);
    }

    private RequestContent putRequestParametersInRequestContent(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put(EMAIL_PARAM, email);
        requestParameters.put(PASSWORD_PARAM, password);

        return new RequestContent(requestParameters);
    }

    private RequestResult defineRequestResult(HttpServletRequest request, RequestContent content) {
        Map<String, String> wrongValues = content.getWrongValues();
        if ( !wrongValues.isEmpty() ) {
            request.setAttribute(WRONG_VALUES_PARAM, wrongValues);
            request.setAttribute(REQUEST_VALUES_PARAM, content.getRequestParameters());
            return new RequestResult(ProjectConstants.SIGN_IN_PAGE, NavigationType.FORWARD);
        }
        request.getSession().setAttribute(USER_PARAM, content.getSessionAttributes().get(USER_PARAM));
        return new RequestResult(ProjectConstants.HOME_PAGE, NavigationType.REDIRECT);
    }
}
