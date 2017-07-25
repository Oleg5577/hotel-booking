package com.pronovich.hotelbooking.command.impl;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class FindRoomCommand implements Command {

    private static final String FIND_ROOM_PAGE = "jsp/admin/find-room-by-request.jsp";
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
        return new RequestResult(FIND_ROOM_PAGE, NavigationType.FORWARD);
    }
}
