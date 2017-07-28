package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Receiver getReceiver();

    RequestResult execute(HttpServletRequest request);
}
