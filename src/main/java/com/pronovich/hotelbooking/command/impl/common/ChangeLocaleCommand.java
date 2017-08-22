package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    private static final String LANGUAGE_PARAM = "language";
    private static final String RU_LOCALE = "ru_RU";
    private static final String RU_LANGUAGE = "ru";
    private static final String RU_COUNTRY = "RU";

    @Override
    public Receiver getReceiver() {
        //TODO throw new UnsupportedOperationException ????
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String language = request.getParameter(LANGUAGE_PARAM);

        if (RU_LOCALE.equals(language)) {
            Locale.setDefault(new Locale(RU_LANGUAGE, RU_COUNTRY));
        } else {
            Locale.setDefault(Locale.US);
        }
        request.getSession().setAttribute(LANGUAGE_PARAM, language);
        return new RequestResult(ProjectConstants.HOME_PAGE, NavigationType.FORWARD);
    }
}
