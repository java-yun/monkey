package com.monkey.product.dao;

import com.monkey.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @Description
 * @date 2020/11/3 11:53
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * 根据审核状态和是否上架查询
     * @param auditStatus 审核状态
     * @param isOnSale 是否上架
     * @return List<Product>
     */
    List<Product> findByAuditStatusAndIsOnSale(Byte auditStatus, Byte isOnSale);


}
