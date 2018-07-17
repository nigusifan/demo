package net.intelink.zmframework.component.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.TimeUtil;
import net.intelink.zmframework.component.ICache;
import net.intelink.zmframework.component.ISession;
import net.intelink.zmframework.constants.SysConstants;
import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.model.AuthInfoModel;
import net.intelink.zmframework.model.StatInfo;
import net.intelink.zmframework.model.UserInfoModel;
import net.intelink.zmframework.util.*;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map;

/**
 *
 * @description session组件
 * @author suzhongqiang
 * @date 17/8/5 下午4:32
 * @version 1.0.0
 */
public class SessionImpl implements ISession {

    static LogHelper log = new LogHelper(SessionManagerUtil.class);

    private ICache iCache = AppContextUtil.getBean(RedisCacheImpl.class);

    private String token;

    public SessionImpl(String token){
        this.token = token;
    }

    private void add(String token,String name, String value) {
        String sessionHashTokenKey = MessageFormat.format(SysConstants.SESSION_HASH_TOKEN, token);
        iCache.hput(sessionHashTokenKey,name,value);
        iCache.expire(sessionHashTokenKey, SysConstants.SESSION_VALID_SECONDS);
    }


    public void setAttr(String name, String value) {
        this.add(token, name, value);
    }

    private String get(String token,String name) {
        String sessionHashTokenKey = MessageFormat.format(SysConstants.SESSION_HASH_TOKEN, token);
        return iCache.hget(sessionHashTokenKey,name);
    }


    public String getAttr(String name) {
        return this.get(token, name);
    }

    private void removeAttr(String token,String name) {
        String sessionHashTokenKey = MessageFormat.format(SysConstants.SESSION_HASH_TOKEN, token);
        iCache.hdel(sessionHashTokenKey, name);
    }


    public void removeAttr(String name) {
        this.removeAttr(token, name);
    }

    @Override
    public boolean isValid() {
        String sessionHashTokenKey = MessageFormat.format(SysConstants.SESSION_HASH_TOKEN, token);

        boolean exists = iCache.exists(sessionHashTokenKey);

        if(exists){
            iCache.expire(sessionHashTokenKey, SysConstants.SESSION_VALID_SECONDS);
            return true;
        }
        return false;
    }


    public void removeSession() {
        iCache.delKey(MessageFormat.format(SysConstants.SESSION_HASH_TOKEN,token));
    }

    @Deprecated
    public void removeOldSession() {
        String tmsOldSessionCopy = MessageFormat.format(SysConstants.TMS_OLD_SESSION,token);
        String tmsSession = MessageFormat.format(SysConstants.SESSION_TMS, token);

        iCache.delKey(tmsOldSessionCopy);
        iCache.delKey(tmsSession);
        this.removeSession();
    }


    public void setOldUser(Object obj){
        iCache.delKey(MessageFormat.format(SysConstants.TMS_OLD_SESSION, this.token));
        iCache.put(MessageFormat.format(SysConstants.TMS_OLD_SESSION, this.token),JsonUtil.toJson(obj));
    }


    @Deprecated
    public UserInfoModel getOldUser() {
        String env = SystemPropertiesUtil.getContextProperty("system.environment");

//        if (env.toUpperCase().equals(EnumSysEnv.DEV.name())) {
        String oldUserInfoStr = iCache.get(MessageFormat.format(SysConstants.TMS_OLD_SESSION, this.token));

        if(StringUtil.isNotEmpty(oldUserInfoStr)){

            UserInfoModel userInfoModel = JsonUtil.json2Object(oldUserInfoStr, UserInfoModel.class);

            JSONObject objects = (JSONObject) JSON.parse(oldUserInfoStr);
            Object userInfoMap = objects.get("statInfo");
            if(userInfoMap == null){
            	userInfoMap = objects.get("userInfo");
            }
//            Object userInfoMap = objects.get("userInfo");

            StatInfo statInfo = JsonUtil.map2Object((Map) userInfoMap, StatInfo.class);
            userInfoModel.setStatInfo(statInfo);

            return userInfoModel;
        }

//        }


        this.isValid();
        String userInfoStr = this.getAttr(SysConstants.SESSION_USER_INFO);

        return JsonUtil.json2Object(userInfoStr,UserInfoModel.class);
    }

    public Object getUser() {
        return getAttr(SysConstants.SESSION_USER_INFO);
    }


    @Override
    public void setExtInfo(String name,Object o){
        this.setAttr(name,JsonUtil.toJson(o));
    }

    public Object getExtInfo(String name){
        return this.getAttr(name);
    }

    public void setUser(Object o){
//        setOldUser(o);
    	setOldUser(o);
        setAttr(SysConstants.SESSION_USER_INFO,JsonUtil.toJson(o));
    }

    private void checkParams(String token,String name,Object value){
        if (StringUtil.isEmpty(token))
            throw new BaseException("[token] is null.");

        if (StringUtil.isEmpty(name))
            throw new BaseException("[name] is null.");

        if(value == null)
            throw new BaseException("[value] is null.");

    }


}
