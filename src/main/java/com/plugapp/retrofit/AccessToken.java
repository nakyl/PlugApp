package com.plugapp.retrofit;

import com.plugapp.container.OauthToken;

public class AccessToken {

    private static OauthToken oauthToken = null;

    public static OauthToken getInstance() {

        // TODO implements instance
        return oauthToken;
    }

    public static void setOauthToken(OauthToken oauthToken) {
        AccessToken.oauthToken = oauthToken;
    }
}
