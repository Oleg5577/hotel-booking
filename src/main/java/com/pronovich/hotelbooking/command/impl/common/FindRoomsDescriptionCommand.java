package com.pronovich.hotelbooking.command.impl.common;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public class FindRoomsDescriptionCommand  extends AbstractCommand {

    private static final String ROOM_LIST = "roomList";

    public FindRoomsDescriptionCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = new RequestContent();

        getReceiver().action(CommandType.FIND_ROOMS_DESCRIPTION, content);

        request.setAttribute(ROOM_LIST, content.getRequestAttributes().get(ROOM_LIST));
        return new RequestResult(ProjectConstants.OUR_ROOMS_PAGE, NavigationType.FORWARD);
    }
}
