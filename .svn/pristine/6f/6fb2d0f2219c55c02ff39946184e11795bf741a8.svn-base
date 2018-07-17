package net.intelink.zmframework.aspect;

import net.intelink.zmframework.annotation.Log;
import net.intelink.zmframework.model.MonitorIntfcLogModel;
import net.intelink.zmframework.util.LogHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

/**
 *
 */
@Component
@Aspect
public class PerformanceMonitorAspect {
    private int monitorServiceExecTime = 50;//毫秒
    LogHelper log = new LogHelper(PerformanceMonitorAspect.class);

//net.intelink.tms.web.authz
//    @Around("execution(* net.intelink.*.service.*.*.*(..))")
//    @Around("ControllerPointcut()")1
//    @Around("execution(* net.intelink.*.*.*.*Controller.*(..))")
    public Object performanceMonitorMethod(ProceedingJoinPoint joinPoint) throws Throwable {


        Date startDate = new Date();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object retVal = null ;
        String exStr = "";
        try {
            retVal = joinPoint.proceed();
        }catch (Exception e){
            exStr = this.getExceptionInfo(e);
            throw e;
        }finally {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();

            MonitorIntfcLogModel mifm = new MonitorIntfcLogModel();
            mifm.setRespTime((int)totalTimeMillis);
            mifm.setStartTime(startDate);
            mifm.setEndTime(new Date());
            mifm.setLogContent(exStr);

            setModuleAndEventAndContent(joinPoint,mifm);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String remoteAddr = request.getRemoteAddr();

            mifm.setClientIp(remoteAddr);

            log.info4Intfc(mifm);


        }


        return retVal;
    }

    private void setModuleAndEventAndContent(ProceedingJoinPoint joinPoint, MonitorIntfcLogModel mifm) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log logAnno = this.getAnnotationObj(Log.class, method.getAnnotations());
        RequestMapping requestMappingAnno = this.getAnnotationObj(RequestMapping.class, method.getAnnotations());


        String content = "";
        if (logAnno != null) {
            mifm.setModule(logAnno.module());
            content = logAnno.content();
        }

        if(requestMappingAnno != null){
            mifm.setExecuteEvent(requestMappingAnno.value()[0]);
        }
        StringBuffer logMessage = new StringBuffer();
        // append args
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append("|");
        }

        logMessage.append(content);

        mifm.setLogContent(mifm.getLogContent() + "|" + logMessage.toString());

    }

    private <T> T getAnnotationObj(Class<T> clazz,Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(clazz)) {
                return (T)annotation;
            }
        }

        return null;
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

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void ControllerPointcut() {
    }
}
