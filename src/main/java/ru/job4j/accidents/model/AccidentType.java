package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;

/**
 * AccidentType - модель данных - тип инцидента
 *
 * @author Ilya Kaltygin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidentTypes")
public class AccidentType {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
