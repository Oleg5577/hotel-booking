package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindInfoForAdminAccountCommand implements Command {

    private static final String ADMIN_ACCOUNT = "jsp/admin/admin-account.jsp";

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

        List<RoomOrder> listRoomOrders = (List<RoomOrder>) content.getRequestAttributes().get("listRoomOrders");
        List<RoomRequest> listRoomRequest = (List<RoomRequest>) content.getRequestAttributes().get("listRoomRequests");

        request.setAttribute("listRoomOrders", listRoomOrders);
        request.setAttribute("listRoomRequests", listRoomRequest);

        return new RequestResult(ADMIN_ACCOUNT, NavigationType.FORWARD);
    }
}
