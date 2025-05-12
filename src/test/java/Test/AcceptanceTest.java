package Test;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "Test")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, summary, html:target/cucumber-reports.html, json:target/cucumber.json, junit:target/cucumber.xml")
@ConfigurationParameter(key = "cucumber.snippet-type", value = "camelcase")
@ConfigurationParameter(key = "cucumber.monochrome", value = "true")
public class AcceptanceTest {
}
