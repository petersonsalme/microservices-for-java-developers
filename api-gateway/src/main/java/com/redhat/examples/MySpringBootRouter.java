package com.redhat.examples;

import java.util.ArrayList;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
@ConfigurationProperties(prefix = "gateway")
public class MySpringBootRouter extends RouteBuilder {

    private String springbootsvcurl, microprofilesvcurl;

    private static final String REST_ENDPOINT = new StringBuilder()
        .append("http:%s/api/greeting?httpClient.connectTimeout=1000")
        .append("&bridgeEndpoint=true")
        .append("&copyHeaders=true")
        .append("&connectionClose=true")
        .toString();

    @Override
    public void configure() {
        from("direct:microprofile").streamCaching()
            .toF(REST_ENDPOINT, microprofilesvcurl)
            .log("Response from MicroProfile microservice: ${body}")
            .convertBodyTo(String.class)
            .end();
        
        from("direct:springboot").streamCaching()
            .toF(REST_ENDPOINT, springbootsvcurl)
            .log("Response from SpringBoot microservice: ${body}")
            .convertBodyTo(String.class)
            .end();

        rest()
            .get("/gateway").enableCORS(true)
            .to("direct:gateway");

        from("direct:gateway")
            .multicast(AggregationStrategies.flexible().accumulateInCollection(ArrayList.class))
            .parallelProcessing()
                .to("direct:microprofile")
                .to("direct:springboot")
            .end()
            .marshal().json(JsonLibrary.Jackson)
            .convertBodyTo(String.class);
    }

    public void setSpringbootsvcurl(String springbootsvcurl) {
        this.springbootsvcurl = springbootsvcurl;
    }

    public void setMicroprofilesvcurl(String microprofilesvcurl) {
        this.microprofilesvcurl = microprofilesvcurl;
    }

}
