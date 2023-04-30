package ru.job4j.model;

import lombok.*;

import java.util.Set;


/**
 * Accident - модель данных - правонарушения
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {

    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String text;

    private String address;

    private AccidentType type;

    private Set<Rule> rules;
}
