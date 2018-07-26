package com.everhomes.learning.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(value={"com.everhomes.learning"})
@EnableAutoConfiguration(exclude={
      DataSourceAutoConfiguration.class, 
      HibernateJpaAutoConfiguration.class,
      FreeMarkerAutoConfiguration.class
  })
@EnableCaching
public class EhLearningStartup {
	private static final Logger LOGGER = LoggerFactory.getLogger(EhLearningStartup.class);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        try {
            SpringApplication app = new SpringApplication(EhLearningStartup.class);
            app.setWebEnvironment(true);
            app.setBanner(null);
            
            app.run(args);
        } catch(Throwable e) {
            e.printStackTrace();
            long endTime = System.currentTimeMillis();
            LOGGER.error("Ehlearning application failed to startup, elapse={}", (endTime - startTime));
        }
        
        long endTime = System.currentTimeMillis();
        LOGGER.error("Ehlearning application startup successfully, elapse={}", (endTime - startTime));
    }
}
