package com.sparta.myblog.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
//@org.springframework.context.annotation.PropertySource(value ="application-DB-KEY.properties" ,ignoreResourceNotFound = false)
@Getter
@Setter
@Component
public class PropertySource {


    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


}
