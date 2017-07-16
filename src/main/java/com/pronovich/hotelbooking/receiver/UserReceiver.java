package com.pronovich.hotelbooking.receiver;


import com.pronovich.hotelbooking.content.RequestContent;

public interface UserReceiver extends Receiver {

    void signUp(RequestContent content);
    void signIn(RequestContent content);
    void signOut(RequestContent content);
}
