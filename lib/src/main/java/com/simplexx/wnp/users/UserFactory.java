package com.simplexx.wnp.users;

import com.simplexx.wnp.api.ISessionProvider;

/**
 * Created by wnp on 2018/7/2.
 */

public class UserFactory {

    public UserFactory() {

    }

    public IUser createUserService(ISessionProvider provider) {
        return new UserService(provider);
    }

}
