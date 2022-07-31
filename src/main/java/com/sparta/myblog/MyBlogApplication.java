package com.sparta.myblog;

import com.sparta.myblog.utils.PropertySource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class MyBlogApplication {



    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyBlogApplication.class, args);
    }

}
