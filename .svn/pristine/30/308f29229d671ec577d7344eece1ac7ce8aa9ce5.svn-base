package net.intelink.zmframework.util;

import org.springframework.util.Base64Utils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化反序列化工具
 *
 * @author  suzhongqiang
 * @date 2017.05.31
 */
public class SerializeUtil {
    /**
     * 序列化
     */
    public static byte[] serialize(Object obj) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject(obj);
            byte[] byteArray = baos.toByteArray();
            return byteArray;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unSerialize(byte[] bytes) {

        ByteArrayInputStream bais = null;

        try {
            //反序列化为对象
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("30");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");
        list.add("addresss ss s s s s ");

//        StopWatch s = new StopWatch();
//        s.start();
//        byte[] serialize = SerializeUtil.serialize(list);
//        String str = Base64Util.encode(new String(serialize));
//        Object o = SerializeUtil.unSerialize(Base64Util.decode(str).getBytes());
//        System.out.println((List<String>)o);
//        s.stop();
//        System.out.println(s.getTotalTimeMillis());



        StopWatch s2 = new StopWatch();
        s2.start();
        byte[] serialize1 = SerializeUtil.serialize(list);
        String str1 = Base64Utils.encodeToString(serialize1);
        Object o1 = SerializeUtil.unSerialize(Base64Utils.decodeFromString(str1));
        System.out.println((List<String>)o1);
        s2.stop();
        System.out.println(s2.getTotalTimeMillis());

        StopWatch s3 = new StopWatch();
        s3.start();
        byte[] serialize2 = SerializeUtil.serialize(list);
        String str2 = Base64Utils.encodeToString(serialize2);
        Object o2 = SerializeUtil.unSerialize(Base64Utils.decodeFromString(str2));
        System.out.println((List<String>)o2);
        s3.stop();
        System.out.println(s3.getTotalTimeMillis());


        String str = "sdfjksdljflsjdflksjdkfljlsjdflksjlfja;lfsdkjf;laskjdfl;asjdfl;ajsl;dfjal;sjdkf;asjdfasdfjlasjfl;sajdf上课的肌肤啊发释放啊非山山水水";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String s1 = Base64Utils.encodeToString(str.getBytes());
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

        StopWatch stopWatch8 = new StopWatch();
        stopWatch8.start();
        String s = Base64Utils.encodeToString(str.getBytes());
        stopWatch8.stop();
        System.out.println(stopWatch8.getTotalTimeMillis());



    }

}