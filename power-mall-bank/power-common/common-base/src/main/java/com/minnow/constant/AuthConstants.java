package com.minnow.constant;

public interface AuthConstants {
    /**
    携带token请求头中的key
     */
    String AUTHORIZATION = "Authorization";
    /**
     * token值的前缀
     */
    String BEARER = "bearer ";

    /**
     * redis中存放token值的前缀
     */
    String LOGIN_TOKEN_PREFIX = "login_token:";

    String LOGIN_URL = "/doLogin";

    String LOGOUT_URL = "/doLogout";

    Long TOKEN_TIME = 14400L;
    Long TOKEN_EXPIRE_THRESHOLD_TIME = 60 * 60L;
}
