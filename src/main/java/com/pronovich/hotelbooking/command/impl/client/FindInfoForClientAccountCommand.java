package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public class FindInfoForClientAccountCommand  extends AbstractCommand {

    private static final String USER = "user";
    private static final String ROOM_REQUESTS_LIST = "listRoomRequests";
    private static final String ROOM_ORDERS_LIST = "listRoomOrders";

    public FindInfoForClientAccountCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER);

        RequestContent content = new RequestContent();
        content.addSessionAttribute(USER, user);

        getReceiver().action(CommandType.FIND_INFO_FOR_CLIENT_ACCOUNT, content);

        request.setAttribute(ROOM_REQUESTS_LIST, content.getRequestAttributes().get(ROOM_REQUESTS_LIST));
        request.setAttribute(ROOM_ORDERS_LIST, content.getRequestAttributes().get(ROOM_ORDERS_LIST));

        return new RequestResult(ProjectConstants.PERSONAL_ACCOUNT_PAGE, NavigationType.FORWARD);
    }
}
