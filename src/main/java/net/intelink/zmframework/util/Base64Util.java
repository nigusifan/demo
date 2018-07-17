package net.intelink.zmframework.util;

import org.springframework.util.StopWatch;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 编解码工具类
 *
 * @author suzhongqiang
 * @date 2017.06.06
 */
public class Base64Util {

    /**
     * 编码
     * @param source
     * @return
     */
    public static String encode(String source) {
        return encode(source.getBytes());
    }

    /**
     * 编码
     * @param source
     * @return
     */
    public static String encode(byte[] source) {
        return Base64.getEncoder().encodeToString(source);
    }


    /**
     * 解码
     * @param source
     * @return
     */
    public static byte[] decode(byte[] source) {
        return Base64.getDecoder().decode(source);
    }

    /**
     * 解码
     * @param source
     * @return
     */
    public static String decode(String source) {
        return new String(decode(source.getBytes()));
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("30");
        list.add("address s s s s s");

        String s = "aaa";


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String encode = Base64Util.encode(SerializeUtil.serialize(list));
        System.out.println(encode);


        Object o = SerializeUtil.unSerialize(Base64Util.decode(encode.getBytes()));
        System.out.println(o);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

        String url = "http://193.168.2.2:9090/sss";
        System.out.println(URLEncoder.encode(url));
        System.out.println(Base64Util.encode(url));
        System.out.println(Base64.getUrlEncoder().encodeToString(url.getBytes()));

    }

}
