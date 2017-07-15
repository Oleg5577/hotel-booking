package com.pronovich.hotelbooking.command;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.receiver.impl.CommonReceiverImpl;
import com.pronovich.hotelbooking.receiver.impl.UserReceiverImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CommandType {

    SIGN_IN(new SignInCommand(new UserReceiverImpl())) {
            public void doReceiver(RequestContent content){
            ( (UserReceiverImpl) getCommand().getReceiver() ).signIn(content);
                // добавление результатов метода signIn в content
        }
    },
    SIGN_UP(new SignUpCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
            ( (CommonReceiverImpl) getCommand().getReceiver() ).signUp(content);
            // добавление результатов метода signUp в content
        }
    },
    SIGN_OUT(new SignOutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) {
            ( (UserReceiverImpl) getCommand().getReceiver() ).signOut(content);
            // добавление результатов метода signOut в content
        }
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;

    }
    public AbstractCommand getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent content);

    public static CommandType takeCommandType(AbstractCommand command) {
        ArrayList<CommandType> result = new ArrayList<>();
        List<CommandType> types = Arrays.asList(CommandType.values());
        types.stream().filter(t -> t.getCommand().equals(command)).forEach(t -> result.add(t));
        System.out.println(result.size());
        return result.get(0);
    }
}
