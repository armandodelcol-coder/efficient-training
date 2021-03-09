package br.com.armando.efficienttraining.domain.repository;

import br.com.armando.efficienttraining.domain.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CustomJpaRepository<Project, Long>{

}
