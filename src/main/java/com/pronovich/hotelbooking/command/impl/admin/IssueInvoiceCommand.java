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

public class IssueInvoiceCommand  extends AbstractCommand {

    private static final String ROOM_ORDER_ID = "roomOrderId";
    private static final String ROOM_ORDER_PARAM = "roomOrder";
    private static final String DAYS_NUMBER_PARAM = "daysNumber";

    public IssueInvoiceCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String roomOrderId = request.getParameter(ROOM_ORDER_ID);

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put(ROOM_ORDER_ID, roomOrderId);

        RequestContent content = new RequestContent(requestValues);

        getReceiver().action(CommandType.ISSUE_INVOICE, content);

        request.setAttribute(ROOM_ORDER_PARAM, content.getRequestAttributes().get(ROOM_ORDER_PARAM));
        request.setAttribute(DAYS_NUMBER_PARAM, content.getRequestAttributes().get(DAYS_NUMBER_PARAM));

        return new RequestResult(ProjectConstants.INVOICE_PAGE, NavigationType.FORWARD);
    }
}
