package com.cursosdedesarrollo.webfluxrouterfuntions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MiController {
    WebClient client = WebClient.create("http://localhost:8080");
    @GetMapping("/client")
    public String getData(){

        client
                .get()
                .uri("/router/persons")
                .exchangeToFlux(res -> res.bodyToFlux(Person.class))
                .log()
                .subscribe(person -> {
                    System.out.println("person: " + person);
                });
        return "data";
    }

    @GetMapping("/clientAdd")
    public String addData(){
        PersonDto person = new PersonDto();
        person.setName("Juan");
        person.setLastName("San");
        client
                .post()
                .uri("/router/persons/save")
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .body(Mono.just(person), PersonDto.class)
                .exchangeToMono(res -> res.bodyToMono(Person.class))
                .log()
                .subscribe(persona -> {
                    log.debug("person: " + persona);
                    System.out.println("person: " + persona);
                });
        return "data";
    }


}
