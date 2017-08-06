package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.admin.CreateOrderCommand;
import com.pronovich.hotelbooking.command.impl.admin.FindInfoForAdminAccountCommand;
import com.pronovich.hotelbooking.command.impl.admin.FindRoomCommand;
import com.pronovich.hotelbooking.command.impl.client.AddRoomRequestCommand;
import com.pronovich.hotelbooking.command.impl.common.FindRoomsDescriptionCommand;
import com.pronovich.hotelbooking.command.impl.common.SignInCommand;
import com.pronovich.hotelbooking.command.impl.common.SignOutCommand;
import com.pronovich.hotelbooking.command.impl.common.SignUpCommand;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.impl.AdminReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.ClientReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.CommonReceiverImpl;

public enum CommandType {

    SIGN_IN( new SignInCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            User user = ((CommonReceiverImpl) getCommand().getReceiver()).signIn(content);
            content.addSessionAttribute("user", user);
        }
    },

    SIGN_UP( new SignUpCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver() ).signUp(content);
        }
    },

    SIGN_OUT( new SignOutCommand(new CommonReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
//            ((ClientReceiverImpl) getCommand().getReceiver()).signOut(content);
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
            (  (CommonReceiverImpl) getCommand().getReceiver()).findRoomsDescription(content);
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
