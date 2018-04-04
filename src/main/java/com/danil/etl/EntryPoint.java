package com.danil.etl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
@PropertySource("file:config.properties")
public class EntryPoint implements CommandLineRunner {
    @Autowired
    private Environment env;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(EntryPoint.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        File currentDirectory = new File(new File(".").getAbsolutePath());

        System.out.println(currentDirectory.getCanonicalPath());
        System.out.println(currentDirectory.getAbsolutePath());

        System.out.println("Hello World!!! ->" + env.getProperty("database.url"));
        //do something
    }
}

