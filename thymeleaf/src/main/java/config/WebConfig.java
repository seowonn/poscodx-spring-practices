package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan({"thymeleaf.controller"})
public class WebConfig implements WebMvcConfigurer {
    // Resouce Handler
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("classpath:assets/");
    }

    // Message Source
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/message");
        messageSource.setDefaultEncoding("utf-8");

        return messageSource;
    }

    // Thymeleaf01: Template Resolver
    @Bean
    public SpringResourceTemplateResolver templateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("utf-8");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    // Thymeleaf02: Template Engine
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateEngineMessageSource(messageSource());

        return templateEngine;
    }

    // Thymeleaf03: View Resolver
    @Bean
    public ViewResolver thymeleafViewResolver(ISpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);

        return viewResolver;
    }
}
