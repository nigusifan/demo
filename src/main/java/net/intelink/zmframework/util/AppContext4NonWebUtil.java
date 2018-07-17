package net.intelink.zmframework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 获取ApplicationContext和Object的工具类
 * 针对非web程序或者测试
 *
 * @author suzhongqiang
 * @date 2017.06.06
 */
public class AppContext4NonWebUtil {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-private-context.xml");

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
