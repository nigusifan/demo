package net.intelink.zmframework.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description 线程池
 * @author suzhongqiang
 * @date 17/7/21 下午3:34
 * @version 1.0.0
 */
public class ThreadPoolExecutors {

    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * @function 日志专用线程处理
     * @param runnable 处理任务
     * @return
     * @exception
     * @author suzhongqiang
     * @date 17/7/21 下午3:34
     */
    public static void handleLog(Runnable runnable){
        singleThreadExecutor.submit(runnable);
    }

    public static void main(String[] args) {

    }
}
