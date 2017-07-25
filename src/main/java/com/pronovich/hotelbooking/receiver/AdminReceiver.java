package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface AdminReceiver extends Receiver {

    void findRoomByRequest(RequestContent content);
}
