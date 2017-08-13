package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;

public interface AdminReceiver extends Receiver {

    void findAllRoomsAccordingRequest(RequestContent content);

    void findInfoForAdminAccount(RequestContent content);

    void createOrder(RequestContent content);

    void cancelOrderByAdmin(RequestContent content);

    void cancelRequestByAdmin(RequestContent content);
}
