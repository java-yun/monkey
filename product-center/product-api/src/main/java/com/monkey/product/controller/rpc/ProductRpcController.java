package com.monkey.product.controller.rpc;

import com.monkey.common.response.Response;
import com.monkey.product.request.ProductSubmissionRequest;
import com.monkey.product.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/11 15:41
 */
@RestController
@RequestMapping(value = "/rpc/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRpcController {

    private final ProductService productService;

    public ProductRpcController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 商品提报
     * @param request request
     * @return Response
     */
    @RequestMapping(value = "/submission", method = RequestMethod.POST)
    public Response<String> productSubmission(@RequestBody ProductSubmissionRequest request) {
        this.productService.productSubmission(request);
        return Response.ok();
    }
}
