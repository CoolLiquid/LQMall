package com.simplexx.wnp.service.login;

import com.simplexx.wnp.baselib.ui.ClientSettings;
import com.simplexx.wnp.presenter.resource.api.ILoginService;
import com.simplexx.wnp.users.IUser;

/**
 * Created by wnp on 2018/7/2.
 */

public class LoginService {
    private final IUser iUser;
    private final ClientSettings clientSettings;
    private final ILoginService loginService;

    public LoginService(IUser user, ClientSettings settings, ILoginService loginService) {
        this.iUser = user;
        this.clientSettings = settings;
        this.loginService = loginService;
    }
}
