package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.TaskResourceReference;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskResourceReferenceRepository extends CustomJpaRepository<TaskResourceReference, Long>{

    public Optional<TaskResourceReference> findByIdAndTaskResourceId(Long id, Long taskResourceId);

}
