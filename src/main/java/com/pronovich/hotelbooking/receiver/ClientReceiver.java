package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface ClientReceiver extends Receiver {

    void addRoomRequest(RequestContent content);

    void cancelRequest(RequestContent content);

    void findInfoForClientAccount(RequestContent content);

    void cancelOrder(RequestContent content);
}
