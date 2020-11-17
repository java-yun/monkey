package com.monkey.product.thread;

import com.monkey.common.thread.ThreadPoolManager;

import java.util.Objects;

/**
 *  商品线程池管理 单例
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 15:19
 */
public class ProductThreadPoolManager extends ThreadPoolManager {

    private static volatile ProductThreadPoolManager threadPoolManager = null;

    private static final Object LOCK = new Object();

    private static final String THREAD_POOL_NAME = "com.yun.product";

    private ProductThreadPoolManager() {
        super();
    }

    public static ProductThreadPoolManager getInstance() {
        synchronized (LOCK) {
            if (Objects.isNull(threadPoolManager)) {
                threadPoolManager = new ProductThreadPoolManager();
            }
        }
        return threadPoolManager;
    }

    @Override
    protected int queueSize() {
        return 150;
    }

    @Override
    protected long keepAliveTime() {
        return 60;
    }

    @Override
    protected int maxPoolSize() {
        return 10;
    }

    @Override
    protected int corePoolSize() {
        return 5;
    }

    @Override
    protected String threadPoolName() {
        return THREAD_POOL_NAME;
    }

}
