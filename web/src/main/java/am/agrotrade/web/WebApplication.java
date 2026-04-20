package am.agrotrade.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@ComponentScan(basePackages = {"am.agrotrade.common",
        "am.agrotrade.core",
        "am.agrotrade.web"})
@EntityScan(basePackages = "am.agrotrade.core.model")
@EnableJpaRepositories(basePackages = (("am.agrotrade.core.repository")))
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "am.agrotrade.core")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
