package com.monkey.product.dao;

import com.monkey.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品 repository
 * @author jiangyun
 * @version 0.0.1
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

    /**
     * 查询上架商品总数
     * @return long
     */
    @Query(value = "select count(p.id) from yun_product p where p.is_on_sale = 1 and p.audit_status = 3", nativeQuery = true)
    long countOnSale();

    /**
     * 按照id 排序批量查询
     * @param cursor 起始id
     * @param size 大小
     * @return List<Product>
     */
    @Query(value = "select p.* from yun_product p where p.id > ?1 and p.is_on_sale = 1 and p.audit_status = 3 order by id asc limit ?2", nativeQuery = true)
    List<Product> getOnSaleProductWithLimit(int cursor, int size);
}
