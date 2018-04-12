package com.danil.etl;

import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:app_config.properties")
@EnableAutoConfiguration
public class AppConfig {
}
