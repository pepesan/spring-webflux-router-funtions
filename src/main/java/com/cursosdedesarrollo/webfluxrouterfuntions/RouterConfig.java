package com.cursosdedesarrollo.webfluxrouterfuntions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfig {

    @Autowired
    private PersonService personService;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        // System.out.println("loading router funtion bean");
        return RouterFunctions.route()
                .GET("/router/persons",personService::findAll)
                .GET("/router/persons/stream",personService::getPersons)
                .GET("/router/persons/{input}",personService::findPerson)
                .POST("/router/persons/save",personService::savePerson)
                .build();

    }


    @Bean
    RouterFunction<ServerResponse> home() {
        return route(GET("/route"), request -> ok().body(Mono.just("hola desde spring y configuracion funcional"), String.class));
    }
}