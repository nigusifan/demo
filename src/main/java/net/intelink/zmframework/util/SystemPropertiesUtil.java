package net.intelink.zmframework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 读取系统配置信息
 *
 * @author suzhongqiang
 * @date 2016.09.19
 */
public class SystemPropertiesUtil extends PropertyPlaceholderConfigurer {

    static Properties properties;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {

        super.processProperties(beanFactory, props);
        // load properties to ctxPropertiesMap
        properties = props;
    }

    // static method for accessing context properties
    public static String getContextProperty(String name) {
        return properties.getProperty(name);
    }
}
