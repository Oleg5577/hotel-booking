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

public class ChangeOrderStatusToCheckedInCommand  extends AbstractCommand {

    private static final String ROOM_ORDER_ID = "roomOrderId";

    public ChangeOrderStatusToCheckedInCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomOrderId = request.getParameter(ROOM_ORDER_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ORDER_ID, roomOrderId);

        RequestContent content = new RequestContent(requestValues);

        getReceiver().action(CommandType.CHANGE_ORDER_STATUS_TO_CHECKED_IN, content);

        return new RequestResult(ProjectConstants.FIND_INFO_FOR_ADMIN_ACCOUNT, NavigationType.REDIRECT);
    }
}
