package br.com.fiap.H3althierLiving.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private int age;
    private String gender;

    private Double weight; // In kg
    private Double height; // In meters
    private Integer dailyStepGoal;

    private LocalDate creationDate = LocalDate.now();

    public void setId(Long id) {
        this.id = id;
    }
}