package com.monkey.common.thread;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池管理  基类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 14:37
 */
public abstract class ThreadPoolManager {

    private static final Map<String, ThreadPoolExecutor> EXECUTOR_MAP = Maps.newConcurrentMap();

    private final ThreadPoolExecutor executor;

    protected ThreadPoolManager() {
        if (EXECUTOR_MAP.containsKey(threadPoolName())) {
            executor = EXECUTOR_MAP.get(threadPoolName());
        } else {
            executor = initThreadPoolExecutor();
        }
    }

    private ThreadPoolExecutor initThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize(),
                maxPoolSize(),
                keepAliveTime(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        EXECUTOR_MAP.put(threadPoolName(), threadPoolExecutor);
        return threadPoolExecutor;
    }

    /**
     * 执行task
     * @param runnable task
     */
    public final void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 提交task
     * @param runnable task
     * @return Future<?>
     */
    public final Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    /**
     * 阻塞队列大小
     * @return int
     */
    protected abstract int queueSize();

    /**
     * 线程池中超过corePoolSize数目的空闲线程最大存活时间
     * @return long
     */
    protected abstract long keepAliveTime();

    /**
     * 最大线程数
     * @return int
     */
    protected abstract int maxPoolSize();

    /**
     * 核心线程数
     * @return int
     */
    protected abstract int corePoolSize();

    /**
     * 获取线程池名称
     * @return String
     */
    protected abstract String threadPoolName();

}