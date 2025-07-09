package org.example.taskmanager.cucumber.glue;

import com.google.gson.Gson;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.GsonBuilder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Given("I have the following headers")
    public void iHaveTheFollowingHeaders(DataTable headers) {
        headersTable = headers;
    }

    @When("I make a request to get a task")
    public void iMakeARequestToGetATask() {
        String url = "http://localhost:" + randomServerPort + "/Task";
        Map<String, String > headersMap = headersTable.asMap(String.class, String.class);
        Gson gson = new GsonBuilder().create();

        response = RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .headers(headersMap)
                .get(url);
    }

    @Then("I should get a status code (\\d+$)")
    public void iShouldGetAStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @And("^I should have a response body")
    public void iShouldHaveAResponseBody() {
        response.then().assertThat().body(not(blankOrNullString()));
    }



}
