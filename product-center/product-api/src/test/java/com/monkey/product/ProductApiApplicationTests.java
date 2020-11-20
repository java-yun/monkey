package com.monkey.product;

import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

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
}
