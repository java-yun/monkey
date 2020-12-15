package com.monkey.product.controller;

import com.google.common.collect.Lists;
import com.monkey.common.mdc.MdcCopyHelper;
import com.monkey.common.response.Response;
import com.monkey.product.annotation.RestLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * java8 parallel stream mdc test
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/15 14:35
 */
@Slf4j
@RestController
@SuppressWarnings("all")
public class ParallelStreamMdcController {

    @RestLog
    @RequestMapping(value = "/parallel/mdc", method = RequestMethod.GET)
    public Response<List<String>> test() {
        var list = Lists.newArrayList("aaa", "bbb", "ccc", "ddd", "eee");
        MdcCopyHelper mdcCopyHelper = new MdcCopyHelper();
        List<String> result = list.parallelStream().peek(mdcCopyHelper::set)
                .map(this::replace)
                .filter(this::filter)
                .peek(mdcCopyHelper::clear)
                .collect(Collectors.toList());
        mdcCopyHelper.set();
        log.info("result: {}", result);
        return Response.ok(result);
    }

    private boolean filter(String s) {
        if ("bbb".equals(s)) {
            log.info("bbb is filter.................");
            return false;
        }
        return true;
    }

    private String replace(String s) {
        if ("aaa".equals(s)) {
            log.info("replace aaa with fff...............");
            return "fff";
        }
        return s;
    }
}
