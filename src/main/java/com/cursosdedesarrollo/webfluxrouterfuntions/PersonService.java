package com.cursosdedesarrollo.webfluxrouterfuntions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ReactivePersonRepository personRepository;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        Flux<Person> customerList = this.personRepository.findAll();
        return ServerResponse.ok().body(customerList,Person.class);
    }
    public Mono<ServerResponse> findPerson(ServerRequest request){
        String personId= request.pathVariable("input");
        Mono<Person> personMono = this.personRepository.findById(personId);
        return ServerResponse.ok().body(personMono,Person.class);
    }

    public Mono<ServerResponse> savePerson(ServerRequest request){
        Mono<PersonDto> personMono = request.bodyToMono(PersonDto.class);
        Person person = new Person();
        personMono.map(personDto -> {
            person.setName(personDto.getName());
            person.setLastName(personDto.getLastName());
            this.personRepository.save(person).map(person1 -> {
                log.debug("Person: "+ person1);
                return person1;
            });
            return person;
        });

        return ServerResponse.ok().body(personMono, PersonDto.class);
        /*
        return personMono.map(personDto -> {
            Person person = new Person();
            person.setLastName(personDto.getLastName());
            person.setName(person.getName());
            this.personRepository.save(person);
            return person;
        });

         */
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
