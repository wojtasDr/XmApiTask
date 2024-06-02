package xm.cucumberrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features/"},
        glue = {"xm.steps"},
        plugin = {"pretty", "json:target/cucumber-report/cucumber.json", "html:target/cucumber-report/cucumber.html"}
)
public class RunCucumberIT {
}
