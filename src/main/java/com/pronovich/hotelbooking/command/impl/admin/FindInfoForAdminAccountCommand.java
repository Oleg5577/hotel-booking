package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;

public class FindInfoForAdminAccountCommand implements Command {

    private static final String LIST_ROOM_ORDERS = "listRoomOrders";
    private static final String LIST_ROOM_REQUESTS = "listRoomRequests";

    private Receiver receiver;

    public FindInfoForAdminAccountCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {

        RequestContent content = new RequestContent();

        receiver.action(CommandType.FIND_INFO_FOR_ADMIN_ACCOUNT, content);

        request.setAttribute(LIST_ROOM_ORDERS, content.getRequestAttributes().get(LIST_ROOM_ORDERS));
        request.setAttribute(LIST_ROOM_REQUESTS, content.getRequestAttributes().get(LIST_ROOM_REQUESTS));

        return new RequestResult(ProjectConstants.ADMIN_ACCOUNT_PAGE, NavigationType.FORWARD);
    }
}
