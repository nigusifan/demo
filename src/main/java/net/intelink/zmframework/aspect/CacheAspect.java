package net.intelink.zmframework.aspect;

import net.intelink.zmframework.annotation.GetCache;
import net.intelink.zmframework.component.ICache;
import net.intelink.zmframework.exception.BaseException;
import net.intelink.zmframework.util.Base64Util;
import net.intelink.zmframework.util.JsonUtil;
import net.intelink.zmframework.util.SerializeUtil;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缓存切入类
 *
 * @author suzhongqiang
 * @date 2017.06.03
 *
 */
@Component
@Aspect
public class CacheAspect {
    @Autowired
    private ICache iCache;


    @Pointcut("@annotation(net.intelink.zmframework.annotation.GetCache)")
    public void GetCache() {
    }

    @Around("GetCache()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception{

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Annotation[] annotations = method.getAnnotations();

        HashMap<String, Object> paramsMap = mappingAnno2RealValue(joinPoint, method);

        GetCache annotationObj = getAnnotationObj(GetCache.class, annotations);

        String key = annotationObj.key();
        String value = annotationObj.value();
        int expire = annotationObj.expire();

        List<Object> paramValueList = mappingParamValue(value, paramsMap);

        System.out.println(paramValueList);

        String keyStr = replacePlaceholderForKey(key, paramValueList);
        System.out.println(keyStr+"++++++++");


        String objectStrFromCache = iCache.get(keyStr);
        System.out.println("get:"+objectStrFromCache);

        if (objectStrFromCache != null) {
            Class returnType = method.getReturnType();
//            if (returnType == List.class)
//                return JsonUtil.json2List(objectStrFromCache, returnModel);
//            return JsonUtil.json2Object(objectStrFromCache, returnType);
            return SerializeUtil.unSerialize(Base64Util.decode(objectStrFromCache.getBytes()));
        }

        Object object = invoke(joinPoint);
        if (object == null)
            return null;
        //将数据库中查询的数据放到redis中
        setCacheValue(keyStr, object, expire);
        return object;


    }

    private Object invoke(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T getAnnotationObj(Class<T> clazz,Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(clazz)) {
                return (T)annotation;
            }
        }

        throw new BaseException("Not found annotation.");
    }

    private HashMap<String, Object> mappingAnno2RealValue(ProceedingJoinPoint joinPoint, Method method) throws NoSuchMethodException {
        Object target = joinPoint.getTarget();

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        Class[] parameterTypes =  method.getParameterTypes();

        Method m = target.getClass().getMethod(methodName, parameterTypes);
        Annotation[][] parameterAnnotations = m.getParameterAnnotations();
        HashMap<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            if (parameterAnnotation[0].annotationType().equals(Param.class)) {
                String paramKey = ((Param) parameterAnnotation[0]).value();
                paramsMap.put(paramKey, args[i]);
            }
        }
        return paramsMap;
    }

    private String replacePlaceholderForKey(String key, List<Object> list) {
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(key);
        while (m.find()) {
            String group = m.group();
            int index = Integer.parseInt(m.group(1));
            key = key.replace(group, list.get(index).toString());
        }
        return key;
    }


    private List<Object> mappingParamValue(String value, HashMap<String, Object> paramsMap) {
        if (value.length() > 0) {

            List<Object> paramValueList = new ArrayList<>();
            String[] paramsArr = value.split(",");
            for (String param : paramsArr) {
                if (param != null && param.length() > 0) {
                    Object fieldValue = null;
                    if ( param.indexOf(".")!= -1) {
                        String[] objAndFiedlArr = param.split("\\.");
                        String obj = objAndFiedlArr[0];
                        String field = objAndFiedlArr[1];

                        Object o = paramsMap.get(obj);

                        if(o instanceof Map){
                            fieldValue = ((Map) o).get(field);
                        }else{
                            String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);

                            try {
                                Method method = o.getClass().getMethod(methodName, null);
                                fieldValue = method.invoke(o, null);

                            } catch (Exception e) {
                            }
                        }

                    }else{
                        fieldValue = paramsMap.get(param);

                    }
                    if (fieldValue == null) {
                        throw new BaseException("The mapping value is null.["+param+"]:"+fieldValue);
                    }
                    paramValueList.add(fieldValue);

                }
            }
            return paramValueList;
        }
        return null;
        
    }

    private void setCacheValue(String key, Object value, long expire) {
        System.out.println(key+"-->"+value+"--->"+expire);

        String objStr = Base64Util.encode(SerializeUtil.serialize(value));
        System.out.println("store:"+objStr);
        if (expire < 0) {
            iCache.put(key, objStr);
        } else {
            iCache.put(key, objStr, expire);
        }

    }

    public static void main(String[] args) {

        HashMap<Object, Object> str = new HashMap<>();
        str.put("id", 555);
        str.put("name", "zhangsan");
        str.put("age", 88);

//        String str = "ZHANGSAN";/**/

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        byte[] serialize = SerializeUtil.serialize(str);

        String s = new String(serialize);
        System.out.println(s);

        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println(totalTimeMillis);


        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();


        System.out.println(JsonUtil.toJson(str));

        stopWatch1.stop();
        long totalTimeMillis1 = stopWatch1.getTotalTimeMillis();
        System.out.println(totalTimeMillis1);

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();


        System.out.println(JsonUtil.toJson(str));

        stopWatch2.stop();
        long totalTimeMillis2 = stopWatch2.getTotalTimeMillis();
        System.out.println(totalTimeMillis2);



    }


}
