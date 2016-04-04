package org.mobiletrain.android37_materialdesigndemo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016-03-22.
 */
public class ThreadManager {

    private static ExecutorService service = null;

    private ThreadManager() {
    }

    public static ExecutorService getInstance() {
        if (service == null) {
            service = Executors.newFixedThreadPool(5);
        }
        return service;
    }

    public void runTask(Runnable runnable) {
        service.execute(runnable);
    }
}
