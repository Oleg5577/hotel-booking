package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindInfoForClientAccountCommand implements Command {

    private static final String PERSONAL_ACCOUNT_PAGE = "jsp/client/personal-account.jsp";
    private static final String LIST_ROOM_ORDERS = "listRoomOrders";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";

    private static final String USER = "user";

    private Receiver receiver;

    public FindInfoForClientAccountCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        RequestContent content = new RequestContent();
        content.addSessionAttribute(USER, session.getAttribute(USER));

        receiver.action(CommandType.FIND_INFO_FOR_CLIENT_ACCOUNT, content);

        session.setAttribute(LIST_ROOM_ORDERS, content.getSessionAttributes().get(LIST_ROOM_ORDERS));
        session.setAttribute(LIST_ROOM_REQUESTS, content.getSessionAttributes().get(LIST_ROOM_REQUESTS));

        return new RequestResult(PERSONAL_ACCOUNT_PAGE, NavigationType.REDIRECT);
    }
}
