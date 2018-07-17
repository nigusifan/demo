package net.intelink.zmframework.component;

import net.intelink.zmframework.model.UserInfoModel;

/**
 * @Description
 * @Author suzhongqiang
 * @Date 17/7/1 上午10:41
 * @version 1.0.0
 */
public interface ISession {


    /**
     *
     * @function 设置value到session中
     * @param name
     * @param value
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:20
     */
    void setAttr(String name,String value);

    /**
     *
     * @function 获取session的属性
     * @param name
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:21
     */
    String getAttr(String name);

    /**
     *
     * @function 删除session属性
     * @param name
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:21
     */
    void removeAttr(String name);

    /**
     *
     * @function 激活session有效期
     * @param
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:22
     */
    boolean isValid();

    /**
     *
     * @function 移除session
     * @param
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:23
     */
    void removeSession();

    /**
     *
     * @function 删除老系统session
     * @param
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/5 下午6:24
     */
    void removeOldSession();

    /**
     *
     * @function 获取用户信息
     * @param
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/7 下午2:49
     */
    public Object getUser();//E

    /**
     *
     * @function 设置扩展信息
     * @param name 属性名
     * @param o 属性值
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/7 下午2:50
     */
    void setExtInfo(String name,Object o);

    /**
     *
     * @function 设置用户信息
     * @param o 用户信息模型
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/7 下午3:04
     */
    void setUser(Object o);

    /**
     *
     * @function 获取扩展信息
     * @param name 属性名
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/8/7 下午3:05
     */
    Object getExtInfo(String name);//E

    UserInfoModel getOldUser();


}
