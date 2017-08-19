package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
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
    private static final String WRONG_VALUES_PARAM = "wrongValues";
    private static final String REQUEST_VALUES_PARAM = "requestValues";

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
            request.setAttribute(WRONG_VALUES_PARAM, wrongValues);
            request.setAttribute(REQUEST_VALUES_PARAM, content.getRequestParameters());
            requestResult = new RequestResult(ProjectConstants.ADD_ROOM_REQUEST_PAGE, NavigationType.FORWARD);
        } else {
            requestResult = new RequestResult(ProjectConstants.FIND_INFO_FOR_CLIENT_ACCOUNT, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
