package za.co.recruitmentzone.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${blog.image.volume}")
    private String BLOG_VOLUME_FULL_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/blog-images/**")
                //.addResourceLocations("file:./blog-images/")
                //.addResourceLocations("file:/home/jenkins/RecruitmentZoneApplication/BlogImages/")
                .addResourceLocations("file:"+BLOG_VOLUME_FULL_PATH)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());
    }
}
//