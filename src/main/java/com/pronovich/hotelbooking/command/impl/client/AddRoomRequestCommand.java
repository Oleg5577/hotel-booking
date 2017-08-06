package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AddRoomRequestCommand implements Command {

    private static final String CHECK_IN_REQUEST_PARAM = "checkInRequest";
    private static final String CHECK_OUT_REQUEST_PARAM = "checkOutRequest";
    private static final String ROOM_SIZE_PARAM = "roomSizeRequest";
    private static final String ROOM_TYPE_PARAM = "roomTypeRequest";
    private static final String USER_PARAM = "user";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";

    private static final String ADD_ROOM_REQUEST_PAGE = "jsp/client/add-room-request.jsp";
    private static final String PERSONAL_ACCOUNT_PAGE = "jsp/client/personal-account.jsp";

    private Receiver receiver;

    public AddRoomRequestCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String checkInRequest = request.getParameter(CHECK_IN_REQUEST_PARAM);
        String checkOutRequest = request.getParameter(CHECK_OUT_REQUEST_PARAM);
        String roomSizeRequest = request.getParameter(ROOM_SIZE_PARAM);
        String roomTypeRequest = request.getParameter(ROOM_TYPE_PARAM);
        User user = (User) request.getSession().getAttribute(USER_PARAM);

        HashMap<String, String> requestValues = new HashMap<>();

        requestValues.put(CHECK_IN_REQUEST_PARAM, checkInRequest);
        requestValues.put(CHECK_OUT_REQUEST_PARAM, checkOutRequest);
        requestValues.put(ROOM_SIZE_PARAM, roomSizeRequest);
        requestValues.put(ROOM_TYPE_PARAM, roomTypeRequest);

        RequestContent content = new RequestContent(requestValues);
        content.addSessionAttribute(USER_PARAM, user);

        receiver.action(CommandType.ADD_ROOM_REQUEST, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("requestValues", content.getRequestParameters());
            requestResult = new RequestResult(ADD_ROOM_REQUEST_PAGE, NavigationType.FORWARD);
        } else {
            request.getSession().setAttribute(LIST_ROOM_REQUESTS, content.getSessionAttributes().get(LIST_ROOM_REQUESTS));
            requestResult = new RequestResult(PERSONAL_ACCOUNT_PAGE, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
