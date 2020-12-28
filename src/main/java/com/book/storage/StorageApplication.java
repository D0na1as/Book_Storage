package com.book.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

	@Bean
	public TomcatServletWebServerFactory tomcatCustomizer() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers((connector -> {

			/**
			 * Gets PORT env variable and sets it as tomcat server port.
			 * In Heroku env, the PORT env variable must be used by tomcat
			 * because if not the process will fail.
			 * Error R10 (Boot timeout) -> Web process failed to bind to $PORT within 60 seconds of launch
			 */
			String envPort = System.getenv("PORT");
			// If PORT is NULL, we'll use the default port
			if (envPort != null) {
				connector.setPort(Integer.parseInt(envPort));
			}
		}));
		return factory;
	}

}
