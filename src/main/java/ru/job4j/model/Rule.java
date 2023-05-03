package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Rule - модель данных - статья нарушения
 *
 * @author Ilya Kaltygin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {

    @EqualsAndHashCode.Include
    private int id;

    private String name;
}