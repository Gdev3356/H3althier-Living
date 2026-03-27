package br.com.fiap.H3althierLiving.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_TASKS")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean completed;
    @Column(columnDefinition = "int default 0")
    private Integer calorieImpact = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}