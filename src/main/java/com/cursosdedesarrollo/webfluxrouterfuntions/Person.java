package com.cursosdedesarrollo.webfluxrouterfuntions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "personas")
public class Person {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
}
