package ru.job4j.model;

import lombok.*;

/**
 * AccidentType - модель данных - тип инцидента
 *
 * @author Ilya Kaltygin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {

    @EqualsAndHashCode.Include
    private int id;

    private String name;
}
