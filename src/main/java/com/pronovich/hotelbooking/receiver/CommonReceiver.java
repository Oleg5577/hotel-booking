package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;

public interface CommonReceiver extends Receiver {

    void signUp(RequestContent content);

    User signIn(RequestContent content);

    void findRoomsDescription(RequestContent content);
}
