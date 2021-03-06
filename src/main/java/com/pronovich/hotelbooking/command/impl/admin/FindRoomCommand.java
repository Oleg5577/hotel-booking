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

public class FindRoomCommand  extends AbstractCommand {

    private static final String REQUEST_ID_PARAM = "requestId";
    private static final String ALL_ROOMS_ACCORDING_REQUEST_PARAM = "allRoomsAccordingRequest";

    public FindRoomCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = putRequestParametersInRequestContent(request);

        getReceiver().action(CommandType.FIND_ROOMS_ACCORDING_REQUEST, content);

        request.setAttribute(ALL_ROOMS_ACCORDING_REQUEST_PARAM, content.getRequestAttributes().get(ALL_ROOMS_ACCORDING_REQUEST_PARAM));
        request.setAttribute(REQUEST_ID_PARAM, request.getParameter(REQUEST_ID_PARAM));
        return new RequestResult(ProjectConstants.FIND_ROOM_PAGE, NavigationType.FORWARD);
    }

    private RequestContent putRequestParametersInRequestContent(HttpServletRequest request) {
        String requestId =  request.getParameter(REQUEST_ID_PARAM);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(REQUEST_ID_PARAM, requestId);

        return new RequestContent(requestValues);
    }
}
