package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRoomRequestCommand implements Command {

    private static final String CHECK_IN_REQUEST_PARAM = "check-in-request";
    private static final String CHECK_OUT_REQUEST_PARAM = "check-out-request";
    private static final String ROOM_SIZE_PARAM = "room-size";
    private static final String ROOM_TYPE_PARAM = "room-type";

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
        User user = (User) request.getSession().getAttribute("user");

        HashMap<String, String> requestValues = new HashMap<>();

        requestValues.put("checkInRequest", checkInRequest);
        requestValues.put("checkOutRequest", checkOutRequest);
        requestValues.put("roomSizeRequest", roomSizeRequest);
        requestValues.put("roomTypeRequest", roomTypeRequest);

        RequestContent content = new RequestContent(requestValues);
        content.addSessionAttribute("user", user);

        receiver.action(CommandType.ADD_ROOM_REQUEST, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;
        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("correctValues", content.getRequestParameters());
            requestResult = new RequestResult(ADD_ROOM_REQUEST_PAGE, NavigationType.FORWARD);
        } else {
            //TODO or send redirect???
            requestResult = new RequestResult(PERSONAL_ACCOUNT_PAGE, NavigationType.REDIRECT);

            List<RoomRequest> listRoomRequest = (List<RoomRequest>) content.getSessionAttributes().get("listRoomRequests");
            request.getSession().setAttribute("listRoomRequests", listRoomRequest);
        }
        return requestResult;
    }
}
