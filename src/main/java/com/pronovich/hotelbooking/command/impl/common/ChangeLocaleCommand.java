package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    private static final String HOME_PAGE = "/jsp/home.jsp";
    private static final String LANGUAGE_PARAM = "language";

    @Override
    public Receiver getReceiver() {
        //TODO throw new UnsupportedOperationException ????
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String language = request.getParameter(LANGUAGE_PARAM);

        if (language.equals("ru_RU")) {
            Locale.setDefault(new Locale("ru", "RU"));
        } else {
            Locale.setDefault(Locale.US);
        }
        request.getSession().setAttribute("language", language);
        return new RequestResult(HOME_PAGE, NavigationType.FORWARD);
    }
}
