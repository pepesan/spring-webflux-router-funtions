package com.cursosdedesarrollo.webfluxrouterfuntions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private ReactivePersonRepository personRepository;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        // ServerRequest == HTTPRequest
        Flux<Person> customerList = this.personRepository.findAll();
        return ServerResponse.ok().body(customerList,Person.class);
    }
    public Mono<ServerResponse> findPerson(ServerRequest request){
        String personId= request.pathVariable("input");
        Mono<Person> personMono = this.personRepository.findById(personId);
        return ServerResponse.ok().body(personMono,Person.class);
    }

    public Mono<ServerResponse> savePerson(ServerRequest request){
        Mono<Person> personMono = request.bodyToMono(Person.class);
        Mono<Person> personSaved = personMono
                .flatMap(personRepository::save);
        return ServerResponse
                .status(HttpStatus.CREATED)
                .body(personSaved, Person.class);
    }
    public Mono<ServerResponse> savePerson2(ServerRequest request){
        Mono<PersonDto> personMono = request.bodyToMono(PersonDto.class);
        Mono<Person> savedPerson = personMono.flatMap(personDto -> {
            Person person = new Person();
            person.setName(personDto.getName());
            person.setLastName(personDto.getLastName());
           return personRepository.save(person);
        });

        return ServerResponse
                .status(HttpStatus.CREATED)
                .body(savedPerson, Person.class);
    }

    public Flux<Person> loadAllPersonsStream() {
        long start = System.currentTimeMillis();
        Flux<Person> persons = this.personRepository.findAll();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));
        return persons;
    }
    public Mono<ServerResponse> getPersons(ServerRequest request) {

        Flux<Person> personsStream = this.loadAllPersonsStream();
        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM)
                .body(personsStream, Person.class);
    }
}
