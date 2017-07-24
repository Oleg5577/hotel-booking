package com.pronovich.hotelbooking.command.impl;

import com.pronovich.hotelbooking.command.Command;
import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.NavigationType;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.content.RequestResult;
import com.pronovich.hotelbooking.entity.RoomOrder;
import com.pronovich.hotelbooking.entity.RoomRequest;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.Receiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInCommand implements Command {

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    //TODO page path from properties;
    private static final String SIGN_IN_PAGE = "/jsp/signin.jsp";
    private static final String WELCOME_PAGE = "/jsp/welcome.jsp";

    private Receiver receiver;

    public SignInCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public RequestResult execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM).trim();
        String password = request.getParameter(PASSWORD_PARAM).trim();

        HashMap<String, String> requestValues = new HashMap<>();
        requestValues.put("email", email);
        requestValues.put("password", password);

        RequestContent content = new RequestContent(requestValues);

        receiver.action(CommandType.SIGN_IN, content);

        Map<String, String> wrongValues = content.getWrongValues();

        RequestResult requestResult;

        if ( !wrongValues.isEmpty()) {
            request.setAttribute("wrongValues", wrongValues);
            request.setAttribute("correctValues", content.getParameters());
            requestResult = new RequestResult(SIGN_IN_PAGE, NavigationType.FORWARD);
        } else {
            User user = (User) content.getSessionAttributes().get("user");
            //TODO Raw type GENERIC?????
            List<RoomRequest> listRoomRequest = (List<RoomRequest>) content.getSessionAttributes().get("listRoomRequest");
            List<RoomOrder> listRoomOrders = (List<RoomOrder>) content.getSessionAttributes().get("listRoomRequest");

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("listRoomRequest", listRoomRequest);
            session.setAttribute("listRoomOrders", listRoomOrders);

            requestResult = new RequestResult(WELCOME_PAGE, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
