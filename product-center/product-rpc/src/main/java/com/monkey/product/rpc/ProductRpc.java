package com.monkey.product.rpc;

import com.monkey.common.response.Response;
import com.monkey.product.fallback.ProductRpcFallback;
import com.monkey.product.request.ProductSubmissionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品 rpc
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/16 11:12
 */
@FeignClient(value = "product-center", contextId = "product", fallback = ProductRpcFallback.class)
public interface ProductRpc {

    /**
     * 商品提报
     * @param request request
     * @return Response
     */
    @RequestMapping(value = "/rpc/product/submission", method = RequestMethod.POST)
    @ResponseBody
    Response<String> productSubmission(@RequestBody ProductSubmissionRequest request);
}
