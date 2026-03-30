package am.agrotrade.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"am.agrotrade.common",
        "am.agrotrade.core",
        "am.agrotrade.web"})
@EntityScan(basePackages = "am.agrotrade.core.model")
@EnableJpaRepositories(basePackages = (("am.agrotrade.core.repository")))
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
