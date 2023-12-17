package com.michalenok.wallet.configuration;

import com.michalenok.wallet.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnMissingBean(LoggingAspect.class)
@ConditionalOnProperty(prefix = "starter.logger", name = "include", havingValue = "true")
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect commonPointcuts() {
        return new LoggingAspect();
    }
}
