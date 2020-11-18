package com.yun.job.executor.service.jobhandler.product;

import com.monkey.common.response.Response;
import com.monkey.product.rpc.ProductSyncRpc;
import com.yun.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 11:20
 */
@Component
public class SyncProductToEsJobHandler {

    @Autowired
    private ProductSyncRpc productSyncRpc;

    @XxlJob("syncProductToEsJobHandler")
    public void syncProductToEs() throws Exception {
        Response<String> response = this.productSyncRpc.syncProductToEs();
    }
}
