package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.RequestContent;

public interface Receiver {

    default void action(CommandType type, RequestContent content) {
        type.doReceiver(content);
    }
}
