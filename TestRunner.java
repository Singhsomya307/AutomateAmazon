import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = "src/test/resource/features",
        glue = "stepDefinintions",  // Ensure this points to the correct package
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)

public class TestRunner {
}
