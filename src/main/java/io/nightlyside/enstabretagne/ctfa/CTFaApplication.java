package io.nightlyside.enstabretagne.ctfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class CTFaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CTFaApplication.class, args);
    }
}
