package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.admin.CreateOrderCommand;
import com.pronovich.hotelbooking.command.impl.admin.FindInfoForAdminAccountCommand;
import com.pronovich.hotelbooking.command.impl.admin.FindRoomCommand;
import com.pronovich.hotelbooking.command.impl.client.AddRoomRequestCommand;
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

    FIND_ROOM( new FindRoomCommand( new AdminReceiverImpl()) ) {
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
            ( (ClientReceiverImpl) getCommand().getReceiver() ).cancelRequest(content);
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
