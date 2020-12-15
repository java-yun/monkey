package com.monkey.common.mdc;

import com.monkey.common.utils.Detect;
import org.slf4j.MDC;

import java.util.Map;

/**
 * MDC wrapper help 针对 parallelStream
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/27 15:38
 */
public class MdcCopyHelper {

    private final Map<String, String> contextMap;

    public MdcCopyHelper() {
        contextMap = MDC.getCopyOfContextMap();
    }

    public void set(Object...any) {
        if (Detect.isNotNullOrEmpty(contextMap)) {
            MDC.setContextMap(contextMap);
        }
    }

    public void clear(Object... any) {
        MDC.clear();
    }

    public boolean clearAndFail() {
        MDC.clear();
        return false;
    }

}
