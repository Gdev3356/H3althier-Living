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
    private int calorieImpact; // e.g., -200 for exercise, +500 for a meal

    @ManyToOne
    @JoinColumn(name = "user_id") // This creates the link in the DB
    private User user;

    public void setId(Long id) {
        this.id = id;
    }
}