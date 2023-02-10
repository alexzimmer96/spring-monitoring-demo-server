package com.mtitsolutions.training.monitoring.monitoringdemoservice.interceptor;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.logging.Handler;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpanContextInterceptor implements HandlerInterceptor {

    private final Tracer tracer;

    @Value("${kubernetes.pod}")
    private String pod;

    @Value("${kubernetes.namespace}")
    private String namespace;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerInterceptor.super.preHandle(request, response, handler);
        request.setAttribute("startTime", Instant.now());
        if (this.tracer.currentSpan() != null) {
            this.tracer.currentSpan().tag("pod", this.pod);
            this.tracer.currentSpan().tag("namespace", this.namespace);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if (this.tracer.currentSpan() != null) {
            if (ex != null) {
                this.tracer.currentSpan().error(ex);
            } else if (response.getStatus() >= 400) {
                this.tracer.currentSpan().tag("error", "true");
            }
        }

        if (!request.getRequestURI().equals("/error")) {
            MDC.put("path", request.getRequestURI());
            MDC.put("query", request.getQueryString());
            MDC.put("method", request.getMethod());
            MDC.put("status", Integer.toString(response.getStatus()));
            if (request.getAttribute("startTime") != null) {
                MDC.put("duration_ms", Long.toString(Duration.between((Instant) request.getAttribute("startTime"), Instant.now()).toMillis()));
            }
            log.info("{} {} responded with status {}", request.getMethod(), request.getRequestURI(), response.getStatus());
        }
    }
}
