package com.cursosdedesarrollo.webfluxrouterfuntions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class WebfluxRouterFuntionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxRouterFuntionsApplication.class, args);
    }
    @Bean
    public RouterFunction<ServerResponse> getStudentsRouter() {
        return route(GET("/route"),
                request -> ok().body(Mono.just("Hola"), String.class));
    }

}
