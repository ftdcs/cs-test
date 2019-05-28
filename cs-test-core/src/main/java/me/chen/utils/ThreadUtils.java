package me.chen.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Author: ftdcs
 * @Date: 2019/05/28 0028 16:20
 * @Description:
 */
public class ThreadUtils {

    static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    public static ExecutorService newSingleThreadPool(){
        return new ThreadPoolExecutor(8, 8,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
