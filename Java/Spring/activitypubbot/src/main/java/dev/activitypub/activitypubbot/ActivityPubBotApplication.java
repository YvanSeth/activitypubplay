package dev.activitypub.activitypubbot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ActivityPubBotApplication extends SpringBootServletInitializer {

    //comment below if deploying outside web container -->
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ActivityPubBotApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivityPubBotApplication.class);
    }
	/*
	public static void main(String[] args) {
		SpringApplication.run(ActivityPubBotApplication.class, args);
	}


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
                System.out.println("Beany McBeanface");

                /* String[] beanNames = ctx.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                for (String beanName : beanNames) {
                    System.out.println(beanName);
                }* /
        };
    } */
}
