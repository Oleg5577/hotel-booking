package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.command.impl.AddRoomRequestCommand;
import com.pronovich.hotelbooking.command.impl.SignInCommand;
import com.pronovich.hotelbooking.command.impl.SignOutCommand;
import com.pronovich.hotelbooking.command.impl.SignUpCommand;
import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;
import com.pronovich.hotelbooking.receiver.impl.UserReceiverImpl;

public enum CommandType {

    SIGN_IN( new SignInCommand(new UserReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            User user = ((UserReceiverImpl) getCommand().getReceiver()).signIn(content);
            content.addSessionAttribute("user", user);
        }
    },

    SIGN_UP( new SignUpCommand(new UserReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (UserReceiverImpl) getCommand().getReceiver() ).signUp(content);
        }
    },

    SIGN_OUT( new SignOutCommand(new UserReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
//            ((UserReceiverImpl) getCommand().getReceiver()).signOut(content);
        }
    },

    ADD_ROOM_REQUEST(new AddRoomRequestCommand(new UserReceiverImpl()) ) {
        @Override
        public void doReceiver(RequestContent content) {
            ( (UserReceiverImpl) getCommand().getReceiver() ).addRoomRequest(content);
        }
    };

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent content);

}
