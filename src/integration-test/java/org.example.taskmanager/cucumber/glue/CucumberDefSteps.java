package org.example.taskmanager.cucumber.glue;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberDefSteps {

    @LocalServerPort
    int randomServerPort;

    private Response response;

    private DataTable headersTable;

    private DataTable requestBodyTable;

    private static WireMockServer wireMockServer;
    @BeforeAll
    public static void startWiremock(){
        wireMockServer = new WireMockServer(
                options().dynamicPort().usingFilesUnderDirectory("src/integration-test/resources/wiremock"));
        wireMockServer.start();
    }

    @AfterAll
    public static void stopWiremock(){
        wireMockServer.stop();
    }


}
