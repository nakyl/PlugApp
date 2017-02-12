package com.plugapp.retrofit;

import com.plugapp.MainActivity;
import com.plugapp.container.OauthToken;
import com.plugapp.controller.ControllerToken;

import java.util.Date;

public class AccessToken {

    private static OauthToken oauthToken = null;

    private static Date dateRefresh = null;

    public static OauthToken getInstance() {
        return oauthToken;
    }

    /**
     * This method is only modified by createToken and refreshToken
     * @param oauthTok
     */
    public static void setOauthToken(OauthToken oauthTok) {
        oauthToken = oauthTok;
        dateRefresh = new Date();
    }

    /**
     * It calculate if the token need to be modified.
     * @return
     */
    public static boolean needRefresh() {
        if(dateRefresh == null) {
            return false;
        }
        // Now - Time last token >= Expires milliseconds
        return (new Date().getTime()-dateRefresh.getTime()) >= oauthToken.getExpiresIn()*1*00;
    }
}
