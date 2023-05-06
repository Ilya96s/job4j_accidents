package ru.job4j.accidents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Authority - модель данных роль пользователя
 *
 * @author Ilya Kaltygin
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = "id")
@Entity
@Table(name = "authorities")
public class Authority {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authority;
}
