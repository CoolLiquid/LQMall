package com.simplexx.wnp.users;

import com.simplexx.wnp.api.ISessionProvider;

/**
 * Created by wnp on 2018/7/2.
 */

public class UserService implements IUser {

    ISessionProvider sessionProvider;

    public UserService(ISessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public String getSession() {
        return sessionProvider.getSessionString();
    }
}
