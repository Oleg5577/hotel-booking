package com.pronovich.hotelbooking.receiver;

import com.pronovich.hotelbooking.content.RequestContent;
import com.pronovich.hotelbooking.entity.User;

public interface UserReceiver extends Receiver {

    void signUp(RequestContent content);

    User signIn(RequestContent content);

    RequestContent signOut(RequestContent content);

    void addRoomRequest(RequestContent content);

}
