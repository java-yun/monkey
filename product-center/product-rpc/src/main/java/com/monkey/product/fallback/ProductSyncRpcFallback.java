package com.monkey.product.fallback;

import com.monkey.common.code.BizCode;
import com.monkey.common.response.Response;
import com.monkey.product.rpc.ProductSyncRpc;
import org.springframework.stereotype.Component;

/**
 * 熔断
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 14:00
 */
@Component
public class ProductSyncRpcFallback implements ProductSyncRpc {

    @Override
    public Response<String> syncProductToEs() {
        return Response.error(BizCode.RPC_TIMEOUT);
    }
}
