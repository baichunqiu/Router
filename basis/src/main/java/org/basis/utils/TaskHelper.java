package org.basis.utils;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskHelper {
    private static TaskHelper instance = new TaskHelper();
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;//时间单位 毫秒ms
    private ScheduledExecutorService service;

    public static TaskHelper getInstance() {
        return instance;
    }

    private TaskHelper() {
        service = Executors.newScheduledThreadPool(3);
    }

    /**
     * 执行任务
     * @param runnable
     */
    public void executeTask(Runnable runnable) {
        if (null == service) return;
        service.execute(runnable);
    }

    /**
     * 执行延迟任务
     * @param runnable
     * @param delay 延迟时间
     */
    public void executeTask(Runnable runnable, long delay) {
        if (null == service) return;
        service.schedule(runnable, delay, timeUnit);
    }

}
