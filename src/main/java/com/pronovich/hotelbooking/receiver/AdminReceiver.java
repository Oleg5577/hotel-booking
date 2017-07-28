package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface AdminReceiver extends Receiver {

    void findAllRoomsAccordingRequest(RequestContent content);

    void findInfoForAdminAccount(RequestContent content);

    void createOrder(RequestContent content);
}
