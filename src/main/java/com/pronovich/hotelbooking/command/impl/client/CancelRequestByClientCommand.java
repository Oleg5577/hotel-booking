package com.pronovich.hotelbooking.command.impl.client;

import com.pronovich.hotelbooking.command.AbstractCommand;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.constant.ProjectConstants;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CancelRequestByClientCommand  extends AbstractCommand {

    private static final String ROOM_REQUEST_ID = "roomRequestId";

    public CancelRequestByClientCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomRequestId = request.getParameter(ROOM_REQUEST_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_REQUEST_ID, roomRequestId);

        RequestContent content = new RequestContent(requestValues);

        getReceiver().action(CommandType.CANCEL_REQUEST_BY_CLIENT, content);

        return new RequestResult(ProjectConstants.FIND_INFO_FOR_CLIENT_ACCOUNT, NavigationType.REDIRECT);
    }
}
