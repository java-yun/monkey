package com.monkey.common.thread;

import com.google.common.collect.Maps;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理  基类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 14:37
 */
public abstract class ThreadPoolManager {

    private static final Map<String, ThreadPoolExecutor> EXECUTOR_MAP = Maps.newConcurrentMap();

    private final ThreadPoolExecutor executor;

    protected ThreadPoolManager(String threadNamePrefix) {
        if (EXECUTOR_MAP.containsKey(threadPoolName())) {
            executor = EXECUTOR_MAP.get(threadPoolName());
        } else {
            executor = initThreadPoolExecutor(threadNamePrefix);
        }
    }

    private ThreadPoolExecutor initThreadPoolExecutor(String threadNamePrefix) {
        var threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize(),
                maxPoolSize(),
                keepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize()),
                new NamedThreadFactory(threadNamePrefix),
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
     * 提交task 返回的future.get()会是主线程阻塞，
     *   阻塞原因：在获取线程的执行结果没有获取到，会等待线程执行完成拿到结果，然后执行后面代码
     * @param runnable task
     * @return Future<?>
     */
    public final Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    /**
     * 提交task 返回的future.get()会是主线程阻塞，
     *   阻塞原因：在获取线程的执行结果没有获取到，会等待线程执行完成拿到结果，然后执行后面代码
     * @param callable task
     * @return Future<T>
     */
    public final <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }

    /**
     * 解决mdc子线程无法获取标记问题
     * @param runnable runnable
     */
    public final void wrapExecute(Runnable runnable) {
        executor.execute(wrapRunnable(runnable, this.getContextForTask()));
    }

    /**
     * 解决mdc子线程无法获取标记问题
     * 提交task 返回的future.get()会是主线程阻塞，
     *   阻塞原因：在获取线程的执行结果没有获取到，会等待线程执行完成拿到结果，然后执行后面代码
     * @param runnable task
     * @return Future<?>
     */
    public final Future<?> wrapSubmit(Runnable runnable) {
        return executor.submit(wrapRunnable(runnable, this.getContextForTask()));
    }

    /**
     * 解决mdc子线程无法获取标记问题
     * 提交task 返回的future.get()会是主线程阻塞，
     *   阻塞原因：在获取线程的执行结果没有获取到，会等待线程执行完成拿到结果，然后执行后面代码
     * @param callable task
     * @return Future<T>
     */
    public final <T> Future<T> wrapSubmit(Callable<T> callable) {
        return executor.submit(wrapCallable(callable, this.getContextForTask()));
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

    private Map<String, String> getContextForTask() {
        return MDC.getCopyOfContextMap();
    }

    private <T> Callable<T> wrapCallable(Callable<T> task, final Map<String, String> context) {
        return () -> {
            var previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return task.call();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }

    private Runnable wrapRunnable(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            var previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }

    public static class NamedThreadFactory implements ThreadFactory {

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String namePrefix) {
            var s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this. namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-thread-" + namePrefix + "-";
        }

        @Override
        public Thread newThread(@Nullable Runnable runnable) {
            var thread = new Thread(group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }
}
