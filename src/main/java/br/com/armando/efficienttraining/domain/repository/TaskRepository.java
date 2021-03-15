package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CustomJpaRepository<Task, Long>{

    @Query("from Task t left join fetch t.project ")
    List<Task> findAll();

    List<Task> findAllByProject(Project project);

}
