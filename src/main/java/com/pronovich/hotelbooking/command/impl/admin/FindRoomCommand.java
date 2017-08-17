package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class FindRoomCommand implements Command {

    private Receiver receiver;

    public FindRoomCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String requestId =  request.getParameter("requestId");

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put("requestId", requestId);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.FIND_ROOM, content);

        request.setAttribute("allRoomsAccordingRequest", content.getRequestAttributes().get("allRoomsAccordingRequest"));
        request.setAttribute("requestId", requestId);
        return new RequestResult(ProjectConstants.FIND_ROOM_PAGE, NavigationType.FORWARD);
    }
}
