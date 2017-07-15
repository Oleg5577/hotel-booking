package com.pronovich.hotelbooking.command;


import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutCommand extends AbstractCommand {

    public SignOutCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        // валидация данных запроса Sign Out
        System.out.println("in SignOut Command");
//        super.execute(request);
        // принятие решения о переходе
    }
}
