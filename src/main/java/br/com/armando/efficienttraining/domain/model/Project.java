package br.com.armando.efficienttraining.domain.model;

import br.com.armando.efficienttraining.domain.model.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Project {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @JsonInclude
    @Transient
    private Integer totalTasks;

    @JsonInclude
    @Transient
    private Integer progress;

    public Integer getTotalTasks() {
        return tasks.size();
    }

    public Integer getProgress() {
        Integer totalDone = getTotalTasksDone();
        return totalDone > 0 ? (100 * totalDone) / getTotalTasks() : 0;
    }

    private Integer getTotalTasksDone() {
        return tasks.stream().filter(t -> t.getStatus().equals(TaskStatus.DONE)).collect(Collectors.toList()).size();
    }

}
