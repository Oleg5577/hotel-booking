package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    RequestResult execute(HttpServletRequest request);

    Receiver getReceiver();
}
