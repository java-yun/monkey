package com.monkey.product;

import com.monkey.common.repository.ElasticsearchOperateRepository;
import com.monkey.product.bo.ProductIndex;
import com.monkey.product.constants.BusinessConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class ProductApiApplicationTests {

    @Autowired
    private ElasticsearchOperateRepository elasticsearchRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void getIndexByAliasTest() throws IOException {
        List<String> indexByAlias = this.elasticsearchRepository.getIndexByAlias(BusinessConstants.PRODUCT_INDEX_ALIAS);
        indexByAlias.forEach(System.out::println);
    }

    @Test
    public void createIndexTest() {
        this.elasticsearchRepository.createIndex(ProductIndex.class, "product_index_v1");
        this.elasticsearchRepository.createIndex(ProductIndex.class, "product_index_v2");
    }
}
