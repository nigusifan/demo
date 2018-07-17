package net.intelink.zmframework.util;

import net.intelink.zmframework.enums.EnumLogLevel;
import net.intelink.zmframework.enums.EnumLogType;
import net.intelink.zmframework.model.MonitorIntfcLogModel;
import net.intelink.zmframework.model.MonitorLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.StopWatch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 *
 * @description 日志帮助类
 * @author suzhongqiang
 * @date 17/7/14 下午8:52   
 * @version 1.0.0  
 */
public class LogHelper {

    private Logger log ;
    private static Properties prop ;
    private Class clz ;
    private InetAddress inetAddr ;


    public LogHelper(Class clz){
        this.clz = clz;
        log = LoggerFactory.getLogger(clz);
        prop = getBasePropertiesRelateInfo();

        try {
            inetAddr= InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * @function 接口日志 error级别
     * @param monitorIntfcLogModel 需要写入的实体 必须
     * @param e 异常
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/7/14 下午8:34
     */
    public void error4Intfc(MonitorIntfcLogModel monitorIntfcLogModel,Throwable e){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.DEBUG, EnumLogType.INTERFACE_LOG);
            BeanUtils.copyProperties(monitorIntfcLogModel,logModel);
            logModel.setLogContent(logModel.getLogContent()+"-->"+getExceptionInfo(e));

            printLog(logModel);
        } catch (Exception e1) {
            log.error("",e1);
        }
    }

    /**
     * @function 接口日志 debug级别
     * @param monitorIntfcLogModel 需要写入的模型
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/7/14 下午8:30
     */
    public void debug4Intfc(MonitorIntfcLogModel monitorIntfcLogModel){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.DEBUG, EnumLogType.INTERFACE_LOG);
            BeanUtils.copyProperties(monitorIntfcLogModel,logModel);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * @function 接口日志 info级别
     * @param monitorIntfcLogModel 需要写入的模型
     * @return
     * @exception 
     * @author suzhongqiang
     * @date 17/7/14 下午8:29
     */
    public void info4Intfc(MonitorIntfcLogModel monitorIntfcLogModel){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.INFO, EnumLogType.INTERFACE_LOG);
            BeanUtils.copyProperties(monitorIntfcLogModel,logModel);

            printLog(logModel);
        } catch (BeansException e) {
            log.error("",e);
        }
    }

    /**
     *
     * @function 任务日志 error级别
     * @param content 日志内容
     * @param module 模块（功能）
     * @param t 异常
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/7/14 下午6:48
     */
    public void error4Task(String content,String module,Throwable t){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.ERROR, EnumLogType.TASK_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content+"-->"+getExceptionInfo(t));

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }
    
    /**
     * @function 任务日志 debug级别
     * @param content 日志内容
     * @param module 模块（功能）
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/7/14 下午6:45
     */
    public void debug4Task(String content,String module){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.DEBUG, EnumLogType.TASK_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }


    /**
     * @function 任务日志 info级别
     * @param content 日志内容
     * @param module 模块（功能）
     * @return 
     * @exception 
     * @author suzhongqiang
     * @date 17/7/14 下午6:42
     */
    public void info4Task(String content,String module){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.INFO, EnumLogType.TASK_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 普通业务日志 error级别
     * @param content 日志内容
     * @param module 模块（功能）
     * @param t 异常
     */
    public void error4Biz(String content,String module,Throwable t){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.ERROR, EnumLogType.BIZ_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content+"-->"+getExceptionInfo(t));

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 普通业务日志 debug级别
     * @param content 日志内容
     * @param module 模块（功能）
     */
    public void debug4Biz(String content,String module){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.DEBUG, EnumLogType.BIZ_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 普通业务日志 info级别
     * @param content 日志内容
     * @param module 模块（功能）
     */
    public void info4Biz(String content,String module){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.INFO, EnumLogType.BIZ_LOG);
            logModel.setModule(module);
            logModel.setLogContent(content);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }


    /**
     * 操作日志
     * @param content 操作内容
     * @param operUser 操作人
     */
    public void info4Oper(String content,String operUser){
        try {
            MonitorLogModel logModel = setCommonRequireWriteField(EnumLogLevel.INFO, EnumLogType.OPER_LOG);
            logModel.setOperUser(operUser);
            logModel.setLogContent(content);

            printLog(logModel);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public MonitorLogModel setCommonRequireWriteField(EnumLogLevel logLevel,EnumLogType logType){
        MonitorLogModel logModel = new MonitorLogModel();
        logModel.setCompanyCode(prop.getProperty("companyCode"));
        logModel.setProjectCode(prop.getProperty("projectCode"));
        logModel.setLogLevel(logLevel.getCode());
        logModel.setLogType((logType.getCode()));
        logModel.setClassName(clz.getSimpleName());
//        logModel.setServerIp(inetAddr.getHostName());

        return logModel;
    }

    public void printLog(MonitorLogModel logModel) {
        log.info(JsonUtil.toJson(logModel));

        ThreadPoolExecutors.handleLog(new Runnable() {
            @Override
            public void run() {
                write(logModel);
            }
        });
    }

    public void write(MonitorLogModel monitorLogModel){
        try {
            Map<String,String> map = new HashMap<>();
            map.put("body",JsonUtil.toJson(monitorLogModel));

            HttpClientUtil.httpPost(prop.getProperty("logUrl"),map);
        } catch (Exception e) {
            log.error("",e);
        }

    }



    public static void main(String[] args) throws Exception{
//        LogHelper log = new LogHelper(LogHelper.class);
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        log.info4Biz("提交了n个任务","更新");
//        stopWatch.stop();
//        System.out.println(stopWatch.getTotalTimeMillis());
//
//        StopWatch stopWatch1= new StopWatch();
//        stopWatch1.start();
//        log.info4Biz("提交了n个任务","更新");
//        stopWatch1.stop();
//        System.out.println(stopWatch1.getTotalTimeMillis());
//
//        StopWatch stopWatch2= new StopWatch();
//        stopWatch2.start();
//        log.info4Biz("提交了n个任务","更新");
//        stopWatch2.stop();
//        System.out.println(stopWatch2.getTotalTimeMillis());
//
//        StopWatch stopWatch3= new StopWatch();
//        stopWatch3.start();
//        log.info4Biz("提交了n个任务","更新");
//        stopWatch3.stop();
//        System.out.println(stopWatch3.getTotalTimeMillis());


    }

    private static String getExceptionInfo(Throwable ex) {
        String exStr = "";
        if(ex == null)
            return exStr;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        exStr = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {

        }
        return exStr;
    }


    public Properties getBasePropertiesRelateInfo(){
        try {
            InputStream resourceAsStream =
                    LogHelper.class.getClassLoader().getResourceAsStream("base.properties");
            Properties prop = new Properties();
            prop.load(resourceAsStream);
            return prop;
        } catch (IOException e) {
            log.error("读取项目基本配置失败[base.properties]",e);
        }
        return null;
    }



}
