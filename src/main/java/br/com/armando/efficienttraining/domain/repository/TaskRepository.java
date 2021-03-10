package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CustomJpaRepository<Task, Long>{

    @Query("from Task t where t.id = :taskId and t.project.id = :projectId")
    Optional<Task> findById(Long projectId, Long taskId);

}
