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

    public SignInCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> requestValues = new HashMap<>();
        Map<String,String> wrongValues = new HashMap<>();

        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        if (StringUtils.isEmpty(email)) {
            wrongValues.put("email", "Please enter a email");
        }
        requestValues.put("email", email);

        if ( StringUtils.isEmpty(password)) {
            wrongValues.put("password", "Please, enter a Password");
        } else {
            requestValues.put("password", password);
        }
        //TODO add message email and password don't match

        if ( !wrongValues.isEmpty() ) {
            request.setAttribute("requestValues", requestValues);
            request.setAttribute("wrongValues", wrongValues);
//            forwardToView(SIGN_IN_VIEW, request, response);
        }
        RequestContent content = new RequestContent(requestValues);
        super.getReceiver().action(CommandType.SIGN_IN, content);
//            response.sendRedirect(HOME_CONTROLLER)
        // валидация данных запроса Sign In
        System.out.println("in SignIn Command");
//        super.execute(content);
        // принятие решения о переходе
    }
}
