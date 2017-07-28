package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class AssignRoomToRequestCommand implements Command {

    private static final String ADMIN_ACCOUNT = "jsp/admin/admin-account.jsp";

    private static final String ROOM_ID_PARAM = "roomId";
    private static final String REQUEST_ID_PARAM = "requestId";

    private Receiver receiver;

    public AssignRoomToRequestCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomId = request.getParameter(ROOM_ID_PARAM);
        String requestId = request.getParameter(REQUEST_ID_PARAM);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put("roomId", roomId);
        requestValues.put("requestId", requestId);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.ASSIGN_ROOM_TO_REQUEST, content);

        return new RequestResult(ADMIN_ACCOUNT, NavigationType.FORWARD);
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }
}
