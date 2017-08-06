package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CreateOrderCommand implements Command {

    private static final String ADMIN_ACCOUNT = "jsp/admin/admin-account.jsp";

    private static final String ROOM_ID_PARAM = "roomId";
    private static final String REQUEST_ID_PARAM = "requestId";

    private static final String LIST_ROOM_ORDERS = "listRoomOrders";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";

    private Receiver receiver;

    public CreateOrderCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomId = request.getParameter(ROOM_ID_PARAM);
        String requestId = request.getParameter(REQUEST_ID_PARAM);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ID_PARAM, roomId);
        requestValues.put(REQUEST_ID_PARAM, requestId);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.CREATE_ORDER, content);

        request.setAttribute(LIST_ROOM_ORDERS, content.getRequestAttributes().get(LIST_ROOM_ORDERS));
        request.setAttribute(LIST_ROOM_REQUESTS, content.getRequestAttributes().get(LIST_ROOM_REQUESTS));
        return new RequestResult(ADMIN_ACCOUNT, NavigationType.FORWARD);
    }
}
