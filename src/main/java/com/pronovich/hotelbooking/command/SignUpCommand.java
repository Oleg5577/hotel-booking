package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.Receiver;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SignUpCommand extends AbstractCommand {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEAT_PASSWORD_PARAM = "repeat-password";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_NUMBER_PARAM = "phone-number";

    SignUpCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> correctRequestValues = new HashMap<>();
        Map<String,String> wrongRequestValues = new HashMap<>();

        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();
        String repeatPassword = request.getParameter(REPEAT_PASSWORD_PARAM).trim();
        String name = request.getParameter(NAME_PARAM).trim();
        String surname = request.getParameter(SURNAME_PARAM).trim();
        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAM).trim();

        // TODO добавить валидацию данных запроса Sign Up
        //TODO add localization messages

        if (StringUtils.isEmpty(email)) {
            wrongRequestValues.put("email", "Please enter a email");
        }
        correctRequestValues.put("email", email);

        if ( StringUtils.isEmpty(password)) {
            wrongRequestValues.put("password", "Please, enter a Password");
        } else {
            correctRequestValues.put("password", password);
        }

        if ( StringUtils.isEmpty(repeatPassword)) {
            wrongRequestValues.put("repeatPassword", "Please, repeat Password");
        } else {
            correctRequestValues.put("repeatPassword", repeatPassword);
        }

        if (StringUtils.isEmpty(name)) {
            wrongRequestValues.put("name", "Please enter a Name");
        }
        correctRequestValues.put("name", name);

        if (StringUtils.isEmpty(surname)) {
            wrongRequestValues.put("surname", "Please enter a Surname");
        }
        correctRequestValues.put("surname", surname);

        if (StringUtils.isEmpty(phoneNumber)) {
            wrongRequestValues.put("phoneNumber", "Please enter a Phone number");
        }
        correctRequestValues.put("phoneNumber", phoneNumber);

        correctRequestValues.put("role", "user");

        if ( !wrongRequestValues.isEmpty()) {
            request.setAttribute("correctRequestValues", correctRequestValues);
            request.setAttribute("wrongRequestValues", wrongRequestValues);
//            forwardToView(SIGN_IN_VIEW, request, response);
        } else {
            RequestContent content = new RequestContent(correctRequestValues);
            super.getReceiver().action(CommandType.SIGN_UP, content);

//        super.execute(request);
            // принятие решения о переходе
        }
        }
}
