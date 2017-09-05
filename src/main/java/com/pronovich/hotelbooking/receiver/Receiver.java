package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.RequestContent;

public interface Receiver {

    /**
     * Default Receiver interface method for execution doReceiver method in CommandType
     * which invoke certain method in receiver.
     * @param type is CommandType enum which contains commands and receivers
     * @param content is RequestContent instance which contains all information that necessary for receiver
     */
    default void action(CommandType type, RequestContent content) {
        type.doReceiver(content);
    }
}
