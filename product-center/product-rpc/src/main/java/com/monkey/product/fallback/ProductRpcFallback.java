package com.monkey.product.fallback;

import com.monkey.common.code.BizCode;
import com.monkey.common.response.Response;
import com.monkey.product.request.ProductSubmissionRequest;
import com.monkey.product.rpc.ProductRpc;
import org.springframework.stereotype.Component;

/**
 * 商品熔断
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/16 11:15
 */
@Component
public class ProductRpcFallback implements ProductRpc {

    @Override
    public Response<String> productSubmission(ProductSubmissionRequest request) {
        return Response.error(BizCode.RPC_TIMEOUT);
    }
}
