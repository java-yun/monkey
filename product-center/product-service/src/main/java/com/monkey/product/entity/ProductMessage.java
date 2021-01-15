package com.monkey.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/15 13:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMessage {

    private List<Product> products;

    private boolean stop;
}
