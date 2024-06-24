package br.com.agendaon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AgendaonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendaonApplication.class, args);
    }

}
