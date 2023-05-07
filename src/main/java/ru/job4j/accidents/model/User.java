package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;

/**
 * User - модель данных пользователь
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = "id")
@Entity
@Table(name = "users")
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    private boolean enabled;

}
