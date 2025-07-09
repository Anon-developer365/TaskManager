package org.example.taskmanager;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectDirectories("src/integration-test/resources/features")
@ConfigurationParameter(key= GLUE_PROPERTY_NAME, value = "org.example.taskmanager.cucumber.glue")
public class CucumberTestIT {
}
