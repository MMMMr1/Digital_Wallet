package com.michalenok.wallet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver loaderTemplateResolver = new ClassLoaderTemplateResolver();
        loaderTemplateResolver.setPrefix("/templates/");
        loaderTemplateResolver.setSuffix(".html");
        loaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        loaderTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return loaderTemplateResolver;
    }
}