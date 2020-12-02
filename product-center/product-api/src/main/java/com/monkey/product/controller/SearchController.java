package com.monkey.product.controller;

import com.monkey.common.response.Response;
import com.monkey.product.bo.ProductSearchRequest;
import com.monkey.product.service.ProductSearchService;
import com.monkey.product.vo.ProductSearchResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品搜索 controller
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 9:41
 */
@RestController
@RequestMapping(value = "/search/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    private final ProductSearchService productSearchService;

    SearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    /**
     * 查询商品 es
     * @param productSearchRequest request
     * @return Response<ProductSearchResult>
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response<ProductSearchResult> searchProduct(@RequestBody ProductSearchRequest productSearchRequest) {
        return Response.ok(this.productSearchService.searchProduct(productSearchRequest));
    }
}
