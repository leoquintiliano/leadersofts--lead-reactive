package br.com.apirest.leadersofts.leadcapture.infrastructure.authentication.config;//package br.com.leadersofts.apioktastorage.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:4200")
//                .allowedOrigins("https://app-okta-storage-automation-50c87ba42d16.herokuapp.com")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//
//        registry.addMapping("/veiculo/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:4200")
//                .allowedOrigins("*")
//                .allowedOrigins("https://app-okta-storage-automation-50c87ba42d16.herokuapp.com/")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//
//        registry.addMapping("/observacao/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET","POST","PUT","DELETE");
//
//        registry.addMapping("/veiculo/list")
//                .allowedOrigins("http://localhost:3000").allowedOrigins("http://localhost:4200").allowedOrigins("*").allowedOrigins("https://app-okta-storage-automation-50c87ba42d16.herokuapp.com/").allowedMethods("GET");
//
//    }
//
//}
