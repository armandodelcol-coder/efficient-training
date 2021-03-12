package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.TaskResource;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskResourceRepository extends CustomJpaRepository<TaskResource, Long>{

}
