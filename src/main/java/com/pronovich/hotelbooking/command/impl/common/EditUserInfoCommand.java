package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.Role;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class EditUserInfoCommand implements Command {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phoneNumber";

    private static final String PERSONAL_ACCOUNT_PAGE = "/controller?command=find_info_for_client_account";
    private static final String ADMIN_ACCOUNT_PAGE = "/controller?command=find_info_for_admin_account";
    private static final String EDIT_USER_INFO_PAGE = "jsp/edit-user-info.jsp";
    private static final String USER_PARAM = "user";
    private static final String UPDATED_USER_PARAM = "updatedUser";

    private Receiver receiver;

    public EditUserInfoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String name = request.getParameter(NAME_PARAM).trim();
        String surname = request.getParameter(SURNAME_PARAM).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        HashMap<String, String> requestParameters = new HashMap<>();

        requestParameters.put(EMAIL_PARAM, email);
        requestParameters.put(NAME_PARAM, name);
        requestParameters.put(SURNAME_PARAM, surname);
        requestParameters.put(PHONE_NUMBER_PARAM, phoneNumber);
        requestParameters.put(PASSWORD_PARAM, password);

        RequestContent content = new RequestContent(requestParameters);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        content.addSessionAttribute(USER_PARAM, user);

        receiver.action(CommandType.EDIT_USER_INFO, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            requestResult = new RequestResult(EDIT_USER_INFO_PAGE, NavigationType.FORWARD);
        } else {
            User updatedUser = (User) content.getSessionAttributes().get(UPDATED_USER_PARAM);
            session.setAttribute(USER_PARAM, updatedUser);
            if (user.getRole() == Role.ADMIN) {
                requestResult = new RequestResult(ADMIN_ACCOUNT_PAGE, NavigationType.REDIRECT);
            } else {
                requestResult = new RequestResult(PERSONAL_ACCOUNT_PAGE, NavigationType.REDIRECT);
            }
        }
        return requestResult;
    }
}
