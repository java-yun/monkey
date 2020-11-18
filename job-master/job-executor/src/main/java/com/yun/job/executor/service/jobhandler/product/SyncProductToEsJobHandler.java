package com.yun.job.executor.service.jobhandler.product;

import com.yun.job.core.context.XxlJobHelper;
import com.yun.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 11:20
 */
@Component
public class SyncProductToEsJobHandler {

    @XxlJob("syncProductToEsJobHandler")
    public void syncProductToEs() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
    }
}
