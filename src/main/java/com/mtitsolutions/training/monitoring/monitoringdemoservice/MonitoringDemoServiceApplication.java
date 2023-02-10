package com.mtitsolutions.training.monitoring.monitoringdemoservice;

import com.mtitsolutions.training.monitoring.monitoringdemoservice.interceptor.SpanContextInterceptor;
import io.micrometer.tracing.SpanCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MonitoringDemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringDemoServiceApplication.class, args);
	}

}
