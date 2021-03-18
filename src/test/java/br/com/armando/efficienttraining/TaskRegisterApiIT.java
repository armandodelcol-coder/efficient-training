package br.com.armando.efficienttraining;

import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.model.TaskResource;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
import br.com.armando.efficienttraining.domain.repository.TaskResourceRepository;
import br.com.armando.efficienttraining.domain.service.TaskRegisterService;
import br.com.armando.efficienttraining.util.DatabaseCleaner;
import br.com.armando.efficienttraining.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TaskRegisterApiIT {

    private static final Long TASK_ID_NOT_EXISTENT = 999L;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRegisterService taskRegisterService;

    @Autowired
    private TaskResourceRepository taskResourceRepository;

    private int totalTasks;
    private Project projectTest1;
    private Project projectTest2;
    private Task taskTest1;
    private Task taskTest2;

    // Json
    private String jsonNewTaskCorrectData;
    private String jsonNewTaskWithInvalidData;

    @BeforeAll
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/tasks";

        jsonNewTaskCorrectData = ResourceUtils.getContentFromResource("/json/newTaskCorrectData.json");
        jsonNewTaskWithInvalidData = ResourceUtils.getContentFromResource("/json/newTaskWithInvalidData.json");
    }

    @Test
    public void shouldReturn200_WhenConsultTasks() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn2Tasks_WhenConsultTasks() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(totalTasks));
    }

    @Test
    public void shouldReturnCorrectData_WhenConsultTaskExistent() {
        RestAssured.given()
                .pathParam("taskId", taskTest1.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{taskId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo(taskTest1.getName()))
                .body("description", Matchers.equalTo(taskTest1.getDescription()))
                .body("complexityLevel", Matchers.equalTo(taskTest1.getComplexityLevel()));
    }

    @Test
    public void shouldReturn201AndCorrectBody_WhenRegisterTask() {
        Map<String, Object> jsonMap = new GsonJsonParser().parseMap(jsonNewTaskCorrectData);
        RestAssured.given()
                .body(jsonNewTaskCorrectData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", Matchers.equalTo(jsonMap.get("name")))
                .body("description", Matchers.equalTo(jsonMap.get("description")));
    }

    @Test
    public void shouldReturn400_WhenRegisterTaskWithInvalidData() {
        RestAssured.given()
                .body(jsonNewTaskWithInvalidData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200_WhenUpdateTaskWithCorrectData() {
        RestAssured.given()
                .pathParam("taskId", taskTest2.getId())
                .body(jsonNewTaskCorrectData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{taskId}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn404_WhenUpdateTaskNotFound() {
        RestAssured.given()
                .pathParam("taskId", TASK_ID_NOT_EXISTENT)
                .body(jsonNewTaskCorrectData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{taskId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturn400_WhenUpdateTaskWithInvalidData() {
        RestAssured.given()
                .pathParam("taskId", taskTest1.getId())
                .body(jsonNewTaskWithInvalidData)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{taskId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn204_WhenDeleteTask() {
        RestAssured.given()
                .pathParam("taskId", taskTest2.getId())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/{taskId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturn404_WhenDeleteTaskNotFound() {
        RestAssured.given()
                .pathParam("taskId", TASK_ID_NOT_EXISTENT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/{taskId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturn409_WhenDeleteTaskInUse() {
        RestAssured.given()
                .pathParam("taskId", taskTest1.getId())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/{taskId}")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void shouldReturn400_WhenSetStatusToDoToDone() {
        RestAssured.given()
                .pathParam("taskId", taskTest1.getId())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{taskId}/done")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn204_WhenSetStatusToDoToDoing() {
        RestAssured.given()
                .pathParam("taskId", taskTest1.getId())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{taskId}/doing")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @BeforeEach
    public void clearTablesAndPrepareData() {
        databaseCleaner.clearTables();
        prepareData();
    }

    private void prepareData() {
        projectTest1 = new Project();
        projectTest1.setName("Projeto 1");
        projectRepository.save(projectTest1);

        projectTest2 = new Project();
        projectTest2.setName("Projeto 2");
        projectRepository.save(projectTest2);

        taskTest1 = new Task();
        taskTest1.setName("Task1");
        taskTest1.setDescription("Apenas testando task1");
        taskTest1.setComplexityLevel(1);
        taskTest1.setProject(projectTest1);
        taskRegisterService.save(taskTest1);

        taskTest2 = new Task();
        taskTest2.setName("Task2");
        taskTest2.setDescription("Apenas testando task2");
        taskTest2.setComplexityLevel(1);
        taskTest2.setProject(projectTest1);
        taskRegisterService.save(taskTest2);

        TaskResource taskResource = new TaskResource();
        taskResource.setName("Task Resource");
        taskResource.setDescription("Task Resource Description");
        taskResource.setTask(taskTest1);
        taskResourceRepository.save(taskResource);

        totalTasks = 2;
    }

}
