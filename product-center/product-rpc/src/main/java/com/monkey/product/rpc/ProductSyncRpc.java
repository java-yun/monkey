package com.monkey.product.rpc;

import com.monkey.common.response.Response;
import com.monkey.product.fallback.ProductSyncRpcFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品同步rpc
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 13:53
 */
@FeignClient(value = "product-center", fallback = ProductSyncRpcFallback.class)
public interface ProductSyncRpc {

    /**
     * es商品同步
     * @return Response
     */
    @RequestMapping(value = "/product/syncProductToEs", method = RequestMethod.POST)
    @ResponseBody
    Response<String> syncProductToEs();
}
