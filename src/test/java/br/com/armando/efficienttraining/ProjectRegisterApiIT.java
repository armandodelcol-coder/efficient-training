package br.com.armando.efficienttraining;

import br.com.armando.efficienttraining.domain.model.Project;
import br.com.armando.efficienttraining.domain.model.Task;
import br.com.armando.efficienttraining.domain.repository.ProjectRepository;
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
public class ProjectRegisterApiIT {

    private static final Long PROJECT_ID_NOT_EXISTENT = 999L;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRegisterService taskRegisterService;

    private int totalProjects;
    private Project projectTest1;
    private Project projectTest2;

    // Json
    private String jsonNewProjectCorrectData;

    @BeforeAll
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/projects";

        jsonNewProjectCorrectData = ResourceUtils.getContentFromResource("/json/newProjectCorrectData.json");
    }

    @BeforeEach
    public void clearTablesAndPrepareData() {
        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    public void shouldReturn200_WhenConsultProjects() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn2Projects_WhenConsultProjects() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(totalProjects));
    }

    @Test
    public void shouldReturn201AndCorrectBody_WhenRegisterProject() {
        Map<String, Object> jsonMap = new GsonJsonParser().parseMap(jsonNewProjectCorrectData);
        RestAssured.given()
                .body(jsonNewProjectCorrectData)
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
    public void shouldReturnCorrect_WhenConsultProjectExistent() {
        RestAssured.given()
                .pathParam("projectId", projectTest1.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{projectId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", Matchers.equalTo("Projeto 1"));
    }

    @Test
    public void shouldReturn404_WhenProjectNotFound() {
        RestAssured.given()
                .pathParam("projectId", PROJECT_ID_NOT_EXISTENT)
                .accept(ContentType.JSON)
                .when()
                .get("/{projectId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturn409_WhenDeleteProjectInUse() {
        RestAssured.given()
                .pathParam("projectId", projectTest1.getId())
                .accept(ContentType.JSON)
                .when()
                .delete("/{projectId}")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void shouldReturn404_WhenDeleteProjectNotFound() {
        RestAssured.given()
                .pathParam("projectId", PROJECT_ID_NOT_EXISTENT)
                .accept(ContentType.JSON)
                .when()
                .delete("/{projectId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        projectTest1 = new Project();
        projectTest1.setName("Projeto 1");
        projectRepository.save(projectTest1);

        projectTest2 = new Project();
        projectTest2.setName("Projeto 2");
        projectRepository.save(projectTest2);

        Task task1 = new Task();
        task1.setName("Teste deletar Projeto em uso");
        task1.setDescription("Apenas testando");
        task1.setComplexityLevel(1);
        task1.setProject(projectTest1);
        taskRegisterService.save(task1);


        totalProjects = 2;
    }

}
