package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SignInCommand extends AbstractCommand {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    //TODO page path from properties;
    private static final String SIGN_IN_PAGE = "/jsp/signin.jsp";
    private static final String WELCOME_PAGE = "/jsp/welcome.jsp";

    SignInCommand(Receiver receiver) {
        super(receiver);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put("email", email);
        requestValues.put("password", password);

        RequestContent content = new RequestContent(requestValues);

        getReceiver().action(CommandType.SIGN_IN, content);

        Map<String, String> wrongValues = content.getWrongValues();

        String page;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("correctValues", content.getParameters());
            page = SIGN_IN_PAGE;
        } else {
            User user = (User) content.getSessionAttributes().get("user");
            request.getSession().setAttribute("user", user);
            page = WELCOME_PAGE;
        }
        return page;
    }
}
