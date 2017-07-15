package com.pronovich.hotelbooking.command;


import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand extends AbstractCommand {

    public SignUpCommand(Receiver receiver) {
        super(receiver);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        // валидация данных запроса Sign Up
        System.out.println("in SignUp Command");
//        super.execute(request);
        // принятие решения о переходе
        }
}
