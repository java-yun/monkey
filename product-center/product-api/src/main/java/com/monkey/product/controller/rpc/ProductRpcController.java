package com.monkey.product.controller.rpc;

import com.monkey.common.response.Response;
import com.monkey.product.service.impl.ProductElasticsearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品 rpc 接口
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 14:09
 */
@RequestMapping(value = "/product")
@RestController
public class ProductRpcController {

    private final ProductElasticsearchService productElasticsearchService;

    ProductRpcController(ProductElasticsearchService productElasticsearchService) {
        this.productElasticsearchService = productElasticsearchService;
    }

    /**
     * es 商品同步
     * @return Response
     */
    @RequestMapping(value = "/syncProductToEs", method = RequestMethod.POST)
    Response<String> syncProductToEs() {
        this.productElasticsearchService.execute();
        return Response.ok("success");
    }
}
