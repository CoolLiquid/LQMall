package com.simplexx.wnp.users;

import com.simplexx.wnp.api.ISessionProvider;
import com.simplexx.wnp.baselib.Sessions;
import com.simplexx.wnp.baselib.ui.ClientSettings;
import com.simplexx.wnp.baselib.util.StringUtil;

/**
 * Created by wnp on 2018/6/29.
 */

public class AuthorizationService {
    private final ClientSettings clientSettings;
    private final ISessionProvider sessionProvider;
    private final Object tokenLocker = new Object();
    private final UserFactory userFactory;

    public AuthorizationService(ClientSettings settings, UserFactory userFactory) {
        this.clientSettings = settings;
        this.userFactory = userFactory;
        this.sessionProvider = new ISessionProvider() {
            @Override
            public String getSessionString() {
                return makeCookie(clientSettings.getLastSessions());
            }
        };
    }

    //将Session嵌套到Cookie中
    String makeCookie(Sessions session) {
        if (session == null || StringUtil.isNullOrEmpty(session.getAccessSessions())) {
            return StringUtil.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cookie: ");
        stringBuilder.append(session.getAccessSessions());
        return stringBuilder.toString();
    }

    public IUser user() {

        // TODO: 2018/7/2 此段代码用于刷新Session
        if (!StringUtil.isNullOrEmpty(sessionProvider.getSessionString())) {
            //此处Session不为空，可以进行Session的刷新
        }
        return userFactory.createUserService(sessionProvider);
    }

}
