package com.monkey.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monkey.common.repository.ElasticsearchOperateRepository;
import com.monkey.common.utils.Detect;
import com.monkey.product.bo.ProductIndex;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.entity.Product;
import com.monkey.product.entity.ProductMessage;
import com.monkey.product.service.BaseElasticsearchService;
import com.monkey.product.service.ProductService;
import com.monkey.product.thread.ProductThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 商品 es 操作service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/20 17:48
 */
@Slf4j
@Service
public class ProductElasticsearchService extends BaseElasticsearchService {

    @Value("${monkey.product.parallel:10}")
    private int productParallel;

    private final ProductService productService;

    public ProductElasticsearchService(ElasticsearchOperateRepository elasticsearchOperateRepository, ProductService productService) {
        super(elasticsearchOperateRepository);
        this.productService = productService;
    }

    @Override
    protected Class<?> getClazz() {
        return ProductIndex.class;
    }

    @Override
    protected List<String> getIndexNames() {
        return BusinessConstants.PRODUCT_INDEXES;
    }

    @Override
    protected String getIndexAlias() {
        return BusinessConstants.PRODUCT_INDEX_ALIAS;
    }

    @Override
    protected void syncDataToEs(String indexName) {
        var bulkProcessor = super.elasticsearchOperateRepository.initBulkProcessor(BusinessConstants.PRODUCT_BULK_SIZE);
        var queue = new LinkedBlockingQueue<ProductMessage>(10);
        ProductThreadPoolManager.getInstance().wrapSubmit(new Producer(queue));
        var countDownLatch = new CountDownLatch(productParallel);
        IntStream.range(0, productParallel).forEach((i) ->
                ProductThreadPoolManager.getInstance().wrapSubmit(new Consumer(queue, indexName, bulkProcessor, countDownLatch))
        );
        try {
            countDownLatch.await();
            bulkProcessor.flush();
            log.info("index {} async add data to bulk processor finished, waiting refresh segment to ed disk................................", indexName);
        } catch (InterruptedException e) {
            log.error("countDownLatch await interrupted", e);
        } finally {
            try {
                var awaitClose = bulkProcessor.awaitClose(120L, TimeUnit.SECONDS);
                log.info("whether the index {} insertion was completed in time: {}", indexName, awaitClose);
            } catch (InterruptedException e) {
                log.error("bulkProcessor awaitClose or refresh index {} exception", indexName, e);
            }
        }
    }

    private void addToBulkProcessor(BulkProcessor bulkProcessor, List<Product> products, String indexName) {
        products.forEach(product -> {
            ProductIndex productIndex = ProductIndex.builder().build();
            BeanUtils.copyProperties(product, productIndex);
            IndexRequest request = new IndexRequest(indexName).id(product.getCode() + "_" + product.getId()).source(JSONObject.toJSONString(productIndex), XContentType.JSON).opType(DocWriteRequest.OpType.CREATE);
            bulkProcessor.add(request);
        });
        log.info("index {} add to bulk processor batch size {}", indexName, products.size());
    }

    @Override
    protected Settings getUpdateSettings() {
        return Settings.builder().put("index.number_of_replicas", 1)
                .put("index.refresh_interval", "5s")
                .build();
    }

    public class Producer implements Runnable {

        private final BlockingQueue<ProductMessage> queue;

        public Producer(BlockingQueue<ProductMessage> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            var times = 0;
            var productId = 0;
            try {
                while (true) {
                    var products = productService.getOnSaleProductWithLimit(productId, BusinessConstants.PRODUCT_BATCH_QUERY_SIZE);
                    if (Detect.isNullOrEmpty(products)) {
                        queue.put(ProductMessage.builder().stop(true).build());
                        log.info("put message to queue finished");
                        break;
                    }
                    queue.put(ProductMessage.builder().products(products).stop(true).build());
                    productId = products.get(products.size() - 1).getId();
                    times++;
                }
            } catch (InterruptedException e) {
                log.error("message put interrupted", e);
            }
            log.info("put massage total times {}", times);
        }
    }


    public class Consumer implements Runnable {

        private final BlockingQueue<ProductMessage> queue;

        private final String index;

        private final BulkProcessor bulkProcessor;

        private final CountDownLatch countDownLatch;

        public Consumer(BlockingQueue<ProductMessage> queue, String index, BulkProcessor bulkProcessor, CountDownLatch countDownLatch) {
            this.queue = queue;
            this.index = index;
            this.bulkProcessor = bulkProcessor;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    var message = queue.take();
                    if (message.isStop()) {
                        log.info("product consumer thread {} finished", Thread.currentThread().getName());
                        queue.put(ProductMessage.builder().stop(true).build());
                        break;
                    }
                    addToBulkProcessor(bulkProcessor, message.getProducts(), index);
                }
            } catch (InterruptedException e) {
                log.error("take message from queue interrupted", e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
