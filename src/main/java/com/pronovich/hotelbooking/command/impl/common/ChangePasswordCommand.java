package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordCommand extends AbstractCommand {

    private static final String PASSWORD_PARAM = "password";
    private static final String NEW_PASSWORD_PARAM = "newPassword";
    private static final String REPEAT_NEW_PASSWORD_PARAM = "repeatNewPassword";
    private static final String USER_PARAM = "user";
    private static final String WRONG_VALUES_PARAM = "wrongValues";
    private static final String PASSWORD_CHANGED_SUCCESSFULLY_PARAM = "passwordChangedSuccessfully";

    public ChangePasswordCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String password = request.getParameter(PASSWORD_PARAM).trim();
        String newPassword = request.getParameter(NEW_PASSWORD_PARAM).trim();
        String repeatNewPassword = request.getParameter(REPEAT_NEW_PASSWORD_PARAM).trim();

        HashMap<String, String> requestParameters = new HashMap<>();

        requestParameters.put(PASSWORD_PARAM, password);
        requestParameters.put(NEW_PASSWORD_PARAM, newPassword);
        requestParameters.put(REPEAT_NEW_PASSWORD_PARAM, repeatNewPassword);
        User user = (User) request.getSession().getAttribute(USER_PARAM);

        RequestContent content = new RequestContent(requestParameters);
        content.addSessionAttribute(USER_PARAM, user);

        getReceiver().action(CommandType.CHANGE_PASSWORD, content);

        Map<String, String> wrongValues = content.getWrongValues();
        if ( !wrongValues.isEmpty()) {
            request.setAttribute(WRONG_VALUES_PARAM, wrongValues);
        } else {
            request.setAttribute(PASSWORD_CHANGED_SUCCESSFULLY_PARAM, true);
        }
        return new RequestResult(ProjectConstants.CHANGE_PASSWORD_PAGE, NavigationType.FORWARD);
    }
}
