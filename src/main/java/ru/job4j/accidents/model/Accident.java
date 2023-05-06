package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
@ToString(exclude = {"type", "rules"})
@Entity
@Table(name = "accidents")
public class Accident {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String text;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private AccidentType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accidents_rules",
            joinColumns = {@JoinColumn(name = "accident_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")})
    private Set<Rule> rules = new HashSet<>();
}
