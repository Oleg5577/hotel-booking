package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CreateOrderCommand  extends AbstractCommand {

    private static final String ROOM_ID_PARAM = "roomId";
    private static final String REQUEST_ID_PARAM = "requestId";

    private static final String LIST_ROOM_ORDERS = "listRoomOrders";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";

    public CreateOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = putRequestParametersInRequestContent(request);

        getReceiver().action(CommandType.CREATE_ORDER, content);

        request.setAttribute(LIST_ROOM_ORDERS, content.getRequestAttributes().get(LIST_ROOM_ORDERS));
        request.setAttribute(LIST_ROOM_REQUESTS, content.getRequestAttributes().get(LIST_ROOM_REQUESTS));

        return new RequestResult(ProjectConstants.FIND_INFO_FOR_ADMIN_ACCOUNT, NavigationType.REDIRECT);
    }

    private RequestContent putRequestParametersInRequestContent(HttpServletRequest request) {
        String roomId = request.getParameter(ROOM_ID_PARAM);
        String requestId = request.getParameter(REQUEST_ID_PARAM);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ID_PARAM, roomId);
        requestValues.put(REQUEST_ID_PARAM, requestId);

        return new RequestContent(requestValues);
    }
}
