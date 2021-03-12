package br.com.armando.efficienttraining.domain.model;

import br.com.armando.efficienttraining.domain.exception.BusinessException;
import br.com.armando.efficienttraining.domain.model.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Task {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer complexityLevel;

    private String observation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TO_DO;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime doingStartedAt;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime doneAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "task")
    private List<TaskResource> resources = new ArrayList<>();

    public void doing() {
        setStatus(TaskStatus.DOING);
        setDoingStartedAt(OffsetDateTime.now());
    }

    public void done() {
        setStatus(TaskStatus.DONE);
        setDoneAt(OffsetDateTime.now());
    }

    private void setStatus(TaskStatus newStatus) {
        if (getStatus().cantChangeStatusTo(newStatus)) {
            throw new BusinessException(
                    String.format("O Status não pode ser alterado de %s para %s",
                            getStatus().getDescription(),
                            newStatus.getDescription())
            );
        }
        this.status = newStatus;
    }
}
