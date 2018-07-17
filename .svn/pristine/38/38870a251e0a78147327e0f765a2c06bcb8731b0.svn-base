package net.intelink.zmframework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取ApplicationContext和Object的工具类
 *
 * @author suzhongqiang
 * @date 2017.06.06
 */
@Component
public class AppContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 获取applicationContext对象
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的class获取对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }


    /**
     * 通过beanid 获取对象
     * @param name
     * @param clazz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name,Class<T> clazz) throws BeansException {
        return applicationContext.getBean(name,clazz);
    }


}