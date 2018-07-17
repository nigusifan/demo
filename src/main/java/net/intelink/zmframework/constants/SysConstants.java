package net.intelink.zmframework.constants;

import com.mysql.jdbc.TimeUtil;

/**
 * @description 系统常量接口
 * @author suzhongqiang
 * @date 17/7/1 下午3:07
 * @version 1.0.0
 */
public interface SysConstants {

    /**
     * session user info
     * SESSION:USER:INFO
     */
    String SESSION_USER_INFO = "SESSION:USER:INFO";

    /**
     * session auth info
     * SESSION:AUTH:INFO
     */
    String SESSION_AUTH_INFO = "SESSION:AUTH:INFO";

    /**
     * session user attr
     * SESSION:ATTR_INFO
     */
    String SESSION_ATTR_INFO = "SESSION:ATTR:INFO";

    long SESSION_VALID_SECONDS = 1800;

    /**
     * SESSION TOKEN
     * {0}:token
     */
    String SESSION_HASH_TOKEN = "SESSION:HASH:{0}";

    /**
     *
     */
    String TMS_OLD_SESSION = "tms-old-session:{0}";

    /**
     * 老系统session
     */
    String SESSION_TMS = "session-tms:{0}";

}
