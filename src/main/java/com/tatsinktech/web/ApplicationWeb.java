package com.tatsinktech.web;


//import com.tatsinktech.web.export.register.XmlViewComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@EnableScheduling
public class ApplicationWeb {
    
   
    public static void main(String[] args) {
       ConfigurableApplicationContext ConfAppContext =  SpringApplication.run(ApplicationWeb.class, args);

    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

}
