package emotion.diary.server.resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${path.file.home}")
    private String homePath;

    @Value("${path.file.image.diary}")
    private String imagePath;
    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resource/img/diary/**") // 웹에서 접근할 url 경로
//                .addResourceLocations("file:///Users/chenchen/Desktop/dev/"); // 실제 파일이 존재하는 경로

//        registry.addResourceHandler("/emotion-diary/images/diary/39/**") // 웹에서 접근할 url 경로
//                .addResourceLocations("file:///C:/emotion-diary/images/diary/39/산.jpg/"); // 실제 파일이 존재하는 경로

//        String resourceLocation = prefix + "//" +
//        registry.addResourceHandler("/images/diary/**")
//                .addResourceLocations("file:/C:/emotion-diary/images/diary/");

        String url = "file:/" + homePath + "/" + imagePath;
        System.out.println("webMvc url : " + url);
        registry.addResourceHandler("/images/diary/**")
                .addResourceLocations(url);
    }

}