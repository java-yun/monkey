package com.monkey.product.service.impl;

import com.google.common.collect.Lists;
import com.monkey.common.constants.TrueFalseFlagConstants;
import com.monkey.common.repository.BatchRepository;
import com.monkey.common.utils.CollectionUtils;
import com.monkey.common.utils.NumberUtils;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.entity.Product;
import com.monkey.product.enums.AuditStatusEnum;
import com.monkey.product.enums.ProductTypeEnum;
import com.monkey.product.repository.ProductRedisRepository;
import com.monkey.product.service.ProductInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品初始化 service 实现
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 17:55
 */
@Slf4j
@Service
public class ProductInitServiceImpl implements ProductInitService {

    private static final List<Byte> PRODUCT_TYPE = List.of(ProductTypeEnum.ORDINARY.getType(), ProductTypeEnum.ACTIVITY.getType());
    private static final List<Byte> AUDIT_STATUS = List.of(AuditStatusEnum.SUBMISSION.getStatus(), AuditStatusEnum.FIRST_TRIAL.getStatus(), AuditStatusEnum.REVIEW_TRIAL.getStatus());
    private static final List<Integer> CATEGORY_ID = List.of(11,12,13,14,21,22,23,24,31,32,33,34,41,42,43,44,51,52,53,54);
    private static final List<Integer> BRAND_ID = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);
    private static final List<Integer> STOCK_NUM = List.of(100,200,300,400,500,600,700,800,900,1000,2000,2500);
    private static final List<String> NAME = List.of("手机","电脑","iPad","鞋子","袜子","衣服","裤子","洗面奶","口罩","电风扇","吹风机","洗衣机","沐浴乳",
            "垃圾袋","月饼","车载香水","行车记录仪","键盘","毛巾","牙膏","洗发水");
    private static final List<String> BRIEF = List.of("销量王","不太行","很nice","一般般","物美价廉","prefect","奈斯","绝了","你值得拥有","爱了爱了",
            "我劝你别买","你忍得住不动手？","你看着办","很垃圾","骗人的玩意","谁买谁知道","妙不可言","真的很烂","买了你就上当了");
    private static final List<String> DESCRIPTION = List.of(
            "之阿斯蒂芬几位  士大夫士大夫哦  奥斯丁可能发你  阿鼠妈妈 安安4",
            "哦哦哦 玩儿票未发生代理费偏空翻了个身看到非  上海 和好的萨尔地方人",
            "票啊没收到吗  维护字典还撒地方  还是什么 其他的 东东",
            "陶冶他剑上剑柳叶就遮满了天，探清水河三生三世  发发发十里桃花",
            "物美价廉prefect奈斯绝了你值得拥有爱了爱了  一生中最爱 ");


    private static final int FOR_LENGTH = 50;

    private final BatchRepository batchRepository;

    private final ProductRedisRepository productRedisRepository;

    public ProductInitServiceImpl(BatchRepository batchRepository, ProductRedisRepository productRedisRepository) {
        this.batchRepository = batchRepository;
        this.productRedisRepository = productRedisRepository;
    }

    @Override
    public void init() {
        for (int i = 0; i < FOR_LENGTH; i++) {
            var products = Lists.newArrayListWithCapacity(BusinessConstants.PRODUCT_BATCH_INSERT_SIZE);
            for (int j = 0; j < BusinessConstants.PRODUCT_BATCH_INSERT_SIZE; j++) {
                var name = CollectionUtils.getListRandomElement(NAME);
                var auditStatus = CollectionUtils.getListRandomElement(AUDIT_STATUS);
                var isOnSale = AuditStatusEnum.REVIEW_TRIAL == AuditStatusEnum.fromValue(auditStatus) ? TrueFalseFlagConstants.TRUE : TrueFalseFlagConstants.FALSE;
                var date = new Date();
                var product = Product.builder().code(this.productRedisRepository.getProductCode())
                        .type(CollectionUtils.getListRandomElement(PRODUCT_TYPE))
                        .name(name)
                        .brandId(CollectionUtils.getListRandomElement(BRAND_ID))
                        .categoryId(CollectionUtils.getListRandomElement(CATEGORY_ID))
                        .price(NumberUtils.getRandomBigDecimal(0.00, 10000))
                        .mainPic("www.baidu.com")
                        .detailPics("www.baidu.com")
                        .brief(CollectionUtils.getListRandomElement(BRIEF))
                        .description(CollectionUtils.getListRandomElement(DESCRIPTION))
                        .auditStatus(auditStatus)
                        .isOnSale(isOnSale)
                        .stockNum(CollectionUtils.getListRandomElement(STOCK_NUM))
                        .createTime(date)
                        .updateTime(date)
                        .createUser(BusinessConstants.DEFAULT_USER)
                        .updateUser(BusinessConstants.DEFAULT_USER)
                        .build();
                if (isOnSale.equals(TrueFalseFlagConstants.TRUE)) {
                    product.setOnSaleTime(date);
                }
                products.add(product);
            }
            this.batchRepository.batchInsert(products);
        }
        log.info("{} insert data to db success......", Thread.currentThread().getName());
    }

}
