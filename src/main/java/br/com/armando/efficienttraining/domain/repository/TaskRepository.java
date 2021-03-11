package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CustomJpaRepository<Task, Long>{

    List<Task> findAllByProject(Project project);

}
