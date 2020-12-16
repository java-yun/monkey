package com.monkey.product.service.impl;

import com.monkey.common.constants.TrueFalseFlagConstants;
import com.monkey.product.dao.ProductRepository;
import com.monkey.product.entity.Product;
import com.monkey.product.enums.AuditStatusEnum;
import com.monkey.product.repository.ProductRedisRepository;
import com.monkey.product.request.ProductSubmissionRequest;
import com.monkey.product.service.ProductService;
import com.monkey.product.utils.ProductParamCheckUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商品服务 service 实现
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 16:21
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductRedisRepository productRedisRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductRedisRepository productRedisRepository) {
        this.productRepository = productRepository;
        this.productRedisRepository = productRedisRepository;
    }

    @Override
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public List<Product> getOnSaleProduct() {
        return this.productRepository.findByAuditStatusAndIsOnSale(AuditStatusEnum.REVIEW_TRIAL.getStatus(), TrueFalseFlagConstants.TRUE);
    }

    @Override
    public boolean hasProductRecord() {
        return this.productRepository.count() > 0;
    }

    @Override
    public long getOnSaleTotalCount() {
        return productRepository.countOnSale();
    }

    @Override
    public List<Product> getOnSaleProductWithLimit(int cursor, int size) {
        return this.productRepository.getOnSaleProductWithLimit(cursor, size);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void productSubmission(ProductSubmissionRequest request) {
        new ProductParamCheckUtils.Builder().productType(request.getType()).build().checkParam();
        var date = new Date();
        var product = Product.builder().build();
        BeanUtils.copyProperties(request, product);
        product.setAuditStatus(AuditStatusEnum.SUBMISSION.getStatus());
        product.setCode(productRedisRepository.getProductCode());
        product.setIsOnSale(TrueFalseFlagConstants.FALSE);
        product.setCreateTime(date);
        product.setUpdateTime(date);
        this.productRepository.save(product);
    }
}
