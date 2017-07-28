package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;

public interface ClientReceiver extends Receiver {

    void addRoomRequest(RequestContent content);

}
