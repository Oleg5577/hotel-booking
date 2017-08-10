package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class CancelOrderByClientCommand implements Command {

    private static final String PERSONAL_ACCOUNT_PAGE = "/controller?command=find_info_for_client_account";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";
    private static final String ROOM_ORDER_ID = "orderId";
    private static final String USER = "user";
    private Receiver receiver;

    public CancelOrderByClientCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomOrderId = request.getParameter(ROOM_ORDER_ID);
        HttpSession session = request.getSession();

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ORDER_ID, roomOrderId);

        RequestContent content = new RequestContent(requestValues);
        content.addSessionAttribute(USER, session.getAttribute(USER));

        receiver.action(CommandType.CANCEL_ORDER_BY_CLIENT, content);

        session.setAttribute(LIST_ROOM_REQUESTS, content.getSessionAttributes().get(LIST_ROOM_REQUESTS));

        return new RequestResult(PERSONAL_ACCOUNT_PAGE, NavigationType.REDIRECT);
    }
}
