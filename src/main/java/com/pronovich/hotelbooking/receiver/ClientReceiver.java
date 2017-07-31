package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface ClientReceiver extends Receiver {

    void addRoomRequest(RequestContent content);

}
