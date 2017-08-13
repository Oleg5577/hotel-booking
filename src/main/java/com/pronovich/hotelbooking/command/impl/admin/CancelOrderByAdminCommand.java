package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CancelOrderByAdminCommand implements Command {

    private static final String ADMIN_ACCOUNT_PAGE = "/controller?command=find_info_for_admin_account";
    private static final String ROOM_ORDER_ID = "roomOrderId";

    private Receiver receiver;

    public CancelOrderByAdminCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomOrderId = request.getParameter(ROOM_ORDER_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ORDER_ID, roomOrderId);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.CANCEL_ORDER_BY_ADMIN, content);

        return new RequestResult(ADMIN_ACCOUNT_PAGE, NavigationType.REDIRECT);
    }
}
