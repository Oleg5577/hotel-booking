package com.pronovich.hotelbooking.command.impl.admin;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;
import com.pronovich.hotelbooking.receiver.impl.AdminReceiverImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CancelRequestByAdminCommand implements Command {

    private static final String ADMIN_ACCOUNT_PAGE = "/controller?command=find_info_for_admin_account";
    private static final String ROOM_REQUEST_ID = "roomRequestId";

    private Receiver receiver;

    public CancelRequestByAdminCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomRequestId = request.getParameter(ROOM_REQUEST_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_REQUEST_ID, roomRequestId);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.CANCEL_REQUEST_BY_ADMIN, content);

        return new RequestResult(ADMIN_ACCOUNT_PAGE, NavigationType.REDIRECT);
    }
}
