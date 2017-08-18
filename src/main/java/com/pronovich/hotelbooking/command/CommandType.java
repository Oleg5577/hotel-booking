package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.admin.*;
import com.pronovich.hotelbooking.command.impl.client.AddRoomRequestCommand;
import com.pronovich.hotelbooking.command.impl.client.CancelOrderByClientCommand;
import com.pronovich.hotelbooking.command.impl.client.CancelRequestByClientCommand;
import com.pronovich.hotelbooking.command.impl.client.FindInfoForClientAccountCommand;
import com.pronovich.hotelbooking.command.impl.common.*;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.impl.AdminReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.ClientReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.CommonReceiverImpl;

public enum CommandType {

    SIGN_IN( new SignInCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ((CommonReceiverImpl) getCommand().getReceiver()).signIn(content);
        }
    },

    SIGN_UP( new SignUpCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver() ).signUp(content);
        }
    },

    EDIT_USER_INFO(new EditUserInfoCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver()).editUserInfo(content);
        }
    },

    SIGN_OUT( new SignOutCommand() ) {
        @Override
        public void doReceiver(RequestContent content) {
            //TODO throw new UnsupportedOperationException ????
            throw new UnsupportedOperationException();
        }
    },

    ADD_ROOM_REQUEST(new AddRoomRequestCommand(new ClientReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).addRoomRequest(content);
        }
    },

    FIND_ROOMS_ACCORDING_REQUEST( new FindRoomCommand( new AdminReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).findAllRoomsAccordingRequest(content);
        }
    },

    FIND_INFO_FOR_CLIENT_ACCOUNT(new FindInfoForClientAccountCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).findInfoForClientAccount(content);
        }
    },

    FIND_INFO_FOR_ADMIN_ACCOUNT(new FindInfoForAdminAccountCommand( new AdminReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).findInfoForAdminAccount(content);
        }
    },

    CREATE_ORDER(new CreateOrderCommand(new AdminReceiverImpl() ) ){
        @Override
        public void doReceiver(RequestContent content) {
            ((AdminReceiverImpl) getCommand().getReceiver() ).createOrder(content);
        }
    },

    FIND_ROOMS_DESCRIPTION(new FindRoomsDescriptionCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            (  (CommonReceiverImpl) getCommand().getReceiver() ).findRoomsDescription(content);
        }
    },

    CHANGE_LOCALE(new ChangeLocaleCommand()) {
        @Override
        public void doReceiver(RequestContent content) {
            //TODO throw new UnsupportedOperationException ????
            throw new UnsupportedOperationException();
        }
    },

    CANCEL_REQUEST_BY_CLIENT(new CancelRequestByClientCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).cancelRequestByClient(content);
        }
    },

    CANCEL_ORDER_BY_CLIENT(new CancelOrderByClientCommand(new ClientReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (ClientReceiverImpl) getCommand().getReceiver() ).cancelOrderByClient(content);
        }
    },

    CANCEL_REQUEST_BY_ADMIN(new CancelRequestByAdminCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).cancelRequestByAdmin(content);
        }
    },

    CANCEL_ORDER_BY_ADMIN(new CancelOrderByAdminCommand(new AdminReceiverImpl())){
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver()).cancelOrderByAdmin(content);
        }
    },

    CHANGE_ORDER_STATUS_TO_PAID(new ChangeOrderStatusToPaidCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToPaid(content);
        }
    },

    CHANGE_ORDER_STATUS_TO_CHECKED_IN(new ChangeOrderStatusToCheckedInCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToCheckedIn(content);
        }
    },

    CHANGE_ORDER_STATUS_TO_CHECKED_OUT(new ChangeOrderStatusToCheckedOutCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).changeOrderStatusToCheckedOut(content);
        }
    },

    ISSUE_INVOICE(new IssueInvoiceCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (AdminReceiverImpl) getCommand().getReceiver() ).issueInvoice(content);
        }
    }

    ;

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent content);
}
