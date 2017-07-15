package com.pronovich.hotelbooking.receiver.impl;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.CommonReceiver;

public class CommonReceiverImpl implements CommonReceiver {

    @Override
    public void signUp(RequestContent requestValues) {
        System.out.println("SignUp dao");
    }
}
