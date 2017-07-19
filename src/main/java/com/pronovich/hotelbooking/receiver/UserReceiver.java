package com.pronovich.hotelbooking.receiver;


import com.pronovich.hotelbooking.content.RequestContent;

public interface UserReceiver extends Receiver {

    RequestContent signUp(RequestContent content);
    RequestContent signIn(RequestContent content);
    RequestContent signOut(RequestContent content);
}
