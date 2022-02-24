package com.neuralgalaxy.commons.tracing.operator;

import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志AOP实现
 *
 * @author haiker
 */
@Aspect
@Slf4j
public class OperatorAspect {

    final OperatorLoggerServer server;
    final OperatorGlobalAttributes attrs;

    public OperatorAspect(OperatorLoggerServer tracingServer, OperatorGlobalAttributes attrs) {
        this.server = tracingServer;
        this.attrs = attrs;
    }

    @Around("@annotation(tracing)")
    public Object beforeController(ProceedingJoinPoint point, Operator tracing) throws Throwable {
        Object out = point.proceed(point.getArgs());
        logger(point, tracing);
        return out;
    }

    public void logger(ProceedingJoinPoint point, Operator tracing) {
        String action = "".equals(tracing.action()) ?
                point.getSignature().getDeclaringType().getSimpleName() + "." + point.getSignature().getName() :
                tracing.value();
        try {
            String message = tracing.value();
            Map<String, Object> args = Map.of("args", point.getArgs());

            if (!tracing.dynamic()) {
                server.log(action, message, args);
                return;
            }

            if (this.attrs != null) {
                Map<String, Object> gargs = this.attrs.getGlobalArgs();
                if (!CollectionUtils.isEmpty(gargs)) {
                    args.putAll(gargs);
                }
            }
            message = evel(message, args);
            server.log(action, message, args);
        } catch (Exception e) {
            log.warn("operator error: ", e);
        }
    }

    Pattern p = Pattern.compile("\\{[^\\}]*\\}");

    public String evel(String input, Map<String, Object> args) {
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer(input);
        while (m.find()) {
            String group = m.group();
            int idx = sb.indexOf(group);
            String path = group.substring(1, group.length() - 1);
            String value = String.valueOf(JSONPath.compile(path).eval(args));
            sb.replace(idx, idx + group.length(), value);
        }
        return sb.toString();
    }

}
