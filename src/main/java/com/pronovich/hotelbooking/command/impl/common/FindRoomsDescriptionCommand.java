package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public class FindRoomsDescriptionCommand implements Command {

    private static final String ROOM_LIST = "roomList";
    private static final String OUR_ROOMS_PAGE = "jsp/our-rooms.jsp";

    private Receiver receiver;

    public FindRoomsDescriptionCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {

        RequestContent content = new RequestContent();
        receiver.action(CommandType.FIND_ROOMS_DESCRIPTION, content);

        request.setAttribute(ROOM_LIST, content.getRequestAttributes().get(ROOM_LIST));
        return new RequestResult(OUR_ROOMS_PAGE, NavigationType.FORWARD);
    }
}
