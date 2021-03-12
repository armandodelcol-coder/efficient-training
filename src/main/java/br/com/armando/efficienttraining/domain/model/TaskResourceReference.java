package br.com.armando.efficienttraining.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class TaskResourceReference {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_resource_id", nullable = false)
    private TaskResource taskResource;

}
