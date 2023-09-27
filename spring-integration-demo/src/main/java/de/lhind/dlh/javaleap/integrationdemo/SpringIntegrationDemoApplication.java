package de.lhind.dlh.javaleap.integrationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;
import org.springframework.integration.ip.tcp.serializer.ByteArraySingleTerminatorSerializer;

@SpringBootApplication
public class SpringIntegrationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationDemoApplication.class, args);


    }

    @Bean
    public AbstractByteArraySerializer codec() {

        ByteArraySingleTerminatorSerializer serializer =
                new ByteArraySingleTerminatorSerializer((byte) '\000');
        serializer.setMaxMessageSize(65536);
        return serializer;
    }

}