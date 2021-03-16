package br.com.armando.efficienttraining;

import br.com.armando.efficienttraining.domain.exception.EntityInUseException;
import br.com.armando.efficienttraining.domain.exception.ProjectNotFoundException;
import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import br.com.armando.efficienttraining.domain.service.ProjectRegisterService;
import br.com.armando.efficienttraining.domain.service.TaskRegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class ProjectRegisterServiceIT {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectRegisterService projectRegisterService;

	@Autowired
	private TaskRegisterService taskRegisterService;

	@Test
	public void shouldRegisterProjectWithSuccess_WhenInputIsCorrect() {
		// cenário
		Project newProject = new Project();
		newProject.setName("Testando a API Efficient Training");

		// ação
		newProject = projectRepository.save(newProject);

		// validação
		Assertions.assertNotNull(newProject);
		Assertions.assertNotNull(newProject.getId());
	}

	@Test
	public void shouldFailRegisterProject_WhenNameIsNull() {
		// cenário
		Project newProject = new Project();
		newProject.setName(null);
		// Sem name

		// ação e validação
		Assertions.assertThrows(
				DataIntegrityViolationException.class,
				() -> projectRepository.save(newProject)
		);
	}

	@Test
	public void shouldFailDeleteProject_WhenProjectIsInUse() {
		// cenário
		Project newProject = new Project();
		newProject.setName("Testando a API Efficient Training");
		newProject = projectRepository.save(newProject);
		Long newProjectId = newProject.getId();
		Task newTask = new Task();
		newTask.setName("Teste deletar Projeto em uso");
		newTask.setDescription("Apenas testando");
		newTask.setComplexityLevel(1);
		newTask.setProject(newProject);
		taskRegisterService.save(newTask);

		// ação e validação
		Assertions.assertThrows(
				EntityInUseException.class,
				() -> projectRegisterService.delete(newProjectId)
		);
	}

	@Test
	public void shouldFailDeleteProject_WhenProjectNotFound() {
		// cenário
		Long projectIdToDelete = 999L;

		// ação e validação
		Assertions.assertThrows(
				ProjectNotFoundException.class,
				() -> projectRegisterService.delete(projectIdToDelete)
		);
	}

}
