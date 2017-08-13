package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface ClientReceiver extends Receiver {

    void addRoomRequest(RequestContent content);

    void cancelRequestByClient(RequestContent content);

    void findInfoForClientAccount(RequestContent content);

    void cancelOrderByClient(RequestContent content);
}
