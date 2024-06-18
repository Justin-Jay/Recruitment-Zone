package za.co.recruitmentzone.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/blog-images/**")
                .addResourceLocations("file:./blog-images/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());
    }
}