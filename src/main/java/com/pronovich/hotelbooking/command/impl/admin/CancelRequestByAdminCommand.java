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

public class CancelRequestByAdminCommand  extends AbstractCommand {

    private static final String ROOM_REQUEST_ID = "roomRequestId";

    public CancelRequestByAdminCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        RequestContent content = putRequestParametersInRequestContent(request);
        getReceiver().action(CommandType.CANCEL_REQUEST_BY_ADMIN, content);
        return new RequestResult(ProjectConstants.FIND_INFO_FOR_ADMIN_ACCOUNT, NavigationType.REDIRECT);
    }

    private RequestContent putRequestParametersInRequestContent(HttpServletRequest request) {
        String roomRequestId = request.getParameter(ROOM_REQUEST_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_REQUEST_ID, roomRequestId);

        return new RequestContent(requestValues);
    }
}
