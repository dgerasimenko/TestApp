package com.danil.etl;

import com.danil.etl.sheduler.TaskSheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("file:db_config.properties")
public class EntryPoint implements CommandLineRunner {

    @Autowired
    private TaskSheduler taskSheduler;

    @Autowired
    private Environment environment;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(EntryPoint.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        taskSheduler.start();
    }
}

