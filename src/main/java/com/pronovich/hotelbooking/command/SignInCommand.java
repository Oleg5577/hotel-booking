package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.Receiver;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SignInCommand extends AbstractCommand {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    SignInCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> correctRequestValues = new HashMap<>();
        Map<String,String> wrongRequestValues = new HashMap<>();

        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        correctRequestValues.put("email", email);

        if ( StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        } else {
            correctRequestValues.put("password", password);
        }
        //TODO add message email and password don't match??

        if ( !wrongRequestValues.isEmpty() ) {
            request.setAttribute("correctRequestValues", correctRequestValues);
            request.setAttribute("wrongRequestValues", wrongRequestValues);
//            forwardToView(SIGN_IN_VIEW, request, response);
        } else {
            RequestContent content = new RequestContent(correctRequestValues);
            super.getReceiver().action(CommandType.SIGN_IN, content);
//            response.sendRedirect(HOME_CONTROLLER)
            // принятие решения о переходе
        }
    }
}
