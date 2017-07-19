package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.command.CommandType;
import com.pronovich.hotelbooking.content.RequestContent;

public interface Receiver {

    default RequestContent action(CommandType type, RequestContent content) {
        return type.doReceiver(content);
    }
}
